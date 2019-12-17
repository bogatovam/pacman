import { Action } from "@ngrx/store";
import { Mode } from "src/app/game/store/game.state";
import { Session } from "src/app/models/session";

export enum GameActionsTypes {
  START_NEW_GAME = '[Game] Start new game',
  SAVE_PLAYER_ID = '[Game] Save player id',
  WAIT_FOR_OTHER_PLAYERS = '[Game] wait for other players to start new game',
  START_CHECKING_SESSION = '[Game] start checking session',
  START_NEW_GAME_SUCCESS = '[Game] Start new game success',
  WATCH_GAME = '[Game] Watch game',
  GAME_OVER = '[Game] Game over ',
  SET_ACTIVE_SESSION_ID = '[Game] Set active session ',
  SET_MODE = '[Game] Set mode ',
}

export class StartNewGame implements Action {
  readonly type = GameActionsTypes.START_NEW_GAME;
}

export class SavePlayerId implements Action {
  readonly type = GameActionsTypes.SAVE_PLAYER_ID;

  constructor(public payload: string) {
  }
}

export class WaitForOtherPlayers implements Action {
  readonly type = GameActionsTypes.WAIT_FOR_OTHER_PLAYERS;

  constructor(public payload: string) {
  }
}

export class StartCheckingSession implements Action {
  readonly type = GameActionsTypes.START_CHECKING_SESSION;

  constructor(public payload: string) {
  }
}

export class StartNewGameSuccess implements Action {
  readonly type = GameActionsTypes.START_NEW_GAME_SUCCESS;
}

export class WatchGame implements Action {
  readonly type = GameActionsTypes.WATCH_GAME;

  constructor(public payload: string) {
  }
}

export class GameOver implements Action {
  readonly type = GameActionsTypes.GAME_OVER;
}

export class SetActiveSessionId implements Action {
  readonly type = GameActionsTypes.SET_ACTIVE_SESSION_ID;

  constructor(public payload: string) {
  }
}

export class SetMode implements Action {
  readonly type = GameActionsTypes.SET_MODE;

  constructor(public payload: Mode) {
  }
}

export type GameActions =
  SetMode |
  GameOver |
  StartCheckingSession |
  SetActiveSessionId |
  SavePlayerId |
  WatchGame |
  WaitForOtherPlayers |
  StartNewGame |
  StartNewGameSuccess
  ;
