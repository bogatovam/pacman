import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from "@ngrx/effects";
import { map, tap } from "rxjs/operators";
import { GameActionsTypes } from "src/app/game/store/game.actions";

@Injectable()
export class GameEffects {
  @Effect({dispatch: false})
  GetAllExercise = this.actions.pipe(
    ofType(GameActionsTypes.GET_MESSAGE_FROM_EFFECT),
    tap(() => console.log("I'm from effect!!!")),
    map((_) => _)
  );

  constructor( private actions: Actions ) { }
}
