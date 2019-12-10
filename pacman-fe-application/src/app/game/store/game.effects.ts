import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from "@ngrx/effects";
import { Action } from "@ngrx/store";
import { Observable, of } from "rxjs";
import { distinctUntilChanged, exhaustMap, map, pluck, switchMap, withLatestFrom } from "rxjs/operators";
import { AuthStoreService } from "src/app/auth/services/auth-store.service";
import { DeltaResolverService } from "src/app/game/services/delta-resolver.service";
import { GameRestService } from "src/app/game/services/game-rest.service";
import { GameSocketService } from "src/app/game/services/game-socket.service";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { GameActionsTypes, SavePlayerId, StartCheckingSession, WaitForOtherPlayers, WaitForOtherPlayersSuccess } from "src/app/game/store/game.actions";
import { SessionDelta } from "src/app/models/session-delta";

@Injectable()
export class GameEffects {

  @Effect()
  startNewGame: Observable<Action[]> = this.actions$.pipe(
    ofType(GameActionsTypes.START_NEW_GAME),
    withLatestFrom(this.authStoreService.retrieveUserId()),
    switchMap(([, userId]: [string, string]) => {
      return this.gameRestService.startNewGame(userId).pipe(
        map((playerId: string) => [playerId, userId])
      );
    }),
    map(([playerId, userId]: [string, string]) => [
      new SavePlayerId(playerId),
      new WaitForOtherPlayers(userId),
    ]),
  );

  @Effect()
  waitForOtherPlayers: Observable<Action[]> = this.actions$.pipe(
    ofType(GameActionsTypes.WAIT_FOR_OTHER_PLAYERS),
    pluck("payload"),
    exhaustMap((userId: string) => this.gameSocketService.buildWaitingGameSocket(userId)),
    distinctUntilChanged(),
    map((sessionId: string) => [
      new WaitForOtherPlayersSuccess(sessionId),
      new StartCheckingSession(sessionId),
    ])
  );

  @Effect()
  startCheckingSession: Observable<Action[]> = this.actions$.pipe(
    ofType(GameActionsTypes.START_CHECKING_SESSION),
    pluck("payload"),
    exhaustMap((sessionId: string) => this.gameSocketService.buildCheckSessionSocket(sessionId)),
    map((delta: SessionDelta) => this.sessionDeltaResolver.resolve(delta))
  );

  constructor(private actions$: Actions,
              private  gameRestService: GameRestService,
              private  gameSocketService: GameSocketService,
              private  authStoreService: AuthStoreService,
              private  sessionDeltaResolver: DeltaResolverService,
  ) { }
}
