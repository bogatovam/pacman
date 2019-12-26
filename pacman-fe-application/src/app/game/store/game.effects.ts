import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from "@ngrx/effects";
import { Action } from "@ngrx/store";
import { Observable } from "rxjs";
import { distinctUntilChanged, exhaustMap, map, mergeMap, pluck, switchMap, withLatestFrom } from "rxjs/operators";
import { AuthStoreService } from "src/app/auth/services/auth-store.service";
import { DeltaResolverService } from "src/app/game/services/delta-resolver.service";
import { GameRestService } from "src/app/game/services/game-rest.service";
import { GameSocketService } from "src/app/game/services/game-socket.service";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { GameActionsTypes, SavePlayerId, SetActiveSessionId, SetMode, StartCheckingSession, UpdateState, WaitForOtherPlayers } from "src/app/game/store/game.actions";
import { Mode } from "src/app/game/store/game.state";
import { Point } from "src/app/models/point";
import { SessionDelta } from "src/app/models/session-delta";

@Injectable()
export class GameEffects {

  @Effect()
  startNewGame: Observable<Action> = this.actions$.pipe(
    ofType(GameActionsTypes.START_NEW_GAME),
    withLatestFrom(this.authStoreService.retrieveUserId()),
    switchMap(([, userId]: [string, string]) => {
      return this.gameRestService.startNewGame(userId).pipe(
        map((playerId: string) => [playerId, userId])
      );
    }),
    mergeMap(([playerId, userId]: [string, string]) => [
      new SetMode(Mode.PLAY),
      new SavePlayerId(playerId),
      new WaitForOtherPlayers(userId),
    ]),
  );

  @Effect()
  waitForOtherPlayers: Observable<Action> = this.actions$.pipe(
    ofType(GameActionsTypes.WAIT_FOR_OTHER_PLAYERS),
    pluck("payload"),
    exhaustMap((userId: string) => this.gameSocketService.buildWaitingGameSocket(userId)),
    distinctUntilChanged(),
    pluck("sessionId"),
    mergeMap((sessionId: string) => [
      new SetActiveSessionId(sessionId),
      new StartCheckingSession(sessionId),
    ])
  );

  @Effect()
  startCheckingSession: Observable<Action> = this.actions$.pipe(
    ofType(GameActionsTypes.START_CHECKING_SESSION),
    pluck("payload"),
    exhaustMap((sessionId: string) => this.gameSocketService.buildCheckSessionSocket(sessionId)),
    mergeMap((delta: SessionDelta) => [new UpdateState(delta.session)])
  );

  @Effect()
  watchGame: Observable<Action> = this.actions$.pipe(
    ofType(GameActionsTypes.WATCH_GAME),
    pluck("payload"),
    withLatestFrom(this.authStoreService.retrieveUserId()),
    exhaustMap(([sessionId, userId]: [string, string]) =>
      this.gameRestService.connectToSession(sessionId, userId).pipe(
        map(() => sessionId)
      )
    ),
    mergeMap((sessionId: string) => [
      new SetMode(Mode.WATCH),
      new SetActiveSessionId(sessionId),
      new StartCheckingSession(sessionId),
    ])
  );

  @Effect({dispatch: false})
  doPlayerAction: Observable<any> = this.actions$.pipe(
    ofType(GameActionsTypes.DO_PLAYER_ACTION),
    pluck("payload"),
    withLatestFrom(this.authStoreService.retrieveUserId(), this.gameStoreService.getGameId()),
    mergeMap(([vector, userId, gameId]: [Point, string, string]) =>
      this.gameRestService.doPlayerAction(userId, gameId, vector).pipe(
        map(() => {
          return {};
        })
      )
    ),
  );

  constructor(private actions$: Actions,
              private  gameRestService: GameRestService,
              private  gameSocketService: GameSocketService,
              private  gameStoreService: GameStoreService,
              private  authStoreService: AuthStoreService,
  ) {
  }
}
