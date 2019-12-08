import { Action } from "@ngrx/store";

export enum GameActionsTypes {
  START_NEW_GAME = '[Game] Start new game',
  START_NEW_GAME_SUCCESS = '[Game] Start new game success',
}

export class StartNewGame implements Action {
  readonly type = GameActionsTypes.START_NEW_GAME;
}

export class StartNewGameSuccess implements Action {
  readonly type = GameActionsTypes.START_NEW_GAME_SUCCESS;

  constructor(public payload: string) { }
}

export type GameActions =
  StartNewGame |
  StartNewGameSuccess
  ;
