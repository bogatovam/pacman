import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from "@ngrx/effects";
import { Action } from "@ngrx/store";
import { Observable } from "rxjs";
import { map, pluck, switchMap, tap, withLatestFrom } from "rxjs/operators";
import { GameRestService } from "src/app/game/services/game.rest.service";
import { GameStoreService } from "src/app/game/services/game.store.service";
import { GameActionsTypes, StartNewGameSuccess } from "src/app/game/store/game.actions";

@Injectable()
export class GameEffects {

  @Effect()
  startNewGame: Observable<Action | string> = this.actions.pipe(
    ofType(GameActionsTypes.START_NEW_GAME),
    withLatestFrom(this.gameStoreService.retrieveUserId()),
    switchMap(([, userId]: [string, string]) => {
      return this.gameRestService.startNewGame(userId);
    }),
    map( (playerId: string) => new StartNewGameSuccess(playerId)),
  );

  constructor( private actions: Actions,
               private  gameRestService: GameRestService,
               private  gameStoreService: GameStoreService,
               ) { }
}
