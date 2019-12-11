import { Action } from "@ngrx/store";

export enum GameActionsTypes {
  START_NEW_GAME = '[Game] Start new game',
  SAVE_PLAYER_ID = '[Game] Save player id',
  WAIT_FOR_OTHER_PLAYERS = '[Game] wait for other players to start new game',
  WAIT_FOR_OTHER_PLAYERS_SUCCESS = '[Game]  wait for other players to start new game success',
  START_CHECKING_SESSION = '[Game] start checking session',
  START_NEW_GAME_SUCCESS = '[Game] Start new game success',
  WATCH_GAME = '[Game] Watch game',
  GAME_OVER = '[Game] Game over ',
}

export class StartNewGame implements Action {
  readonly type = GameActionsTypes.START_NEW_GAME;
}

export class SavePlayerId implements Action {
  readonly type = GameActionsTypes.SAVE_PLAYER_ID;

  constructor(public payload: string) { }
}

export class WaitForOtherPlayers implements Action {
  readonly type = GameActionsTypes.WAIT_FOR_OTHER_PLAYERS;

  constructor(public payload: string) { }
}

export class WaitForOtherPlayersSuccess implements Action {
  readonly type = GameActionsTypes.WAIT_FOR_OTHER_PLAYERS_SUCCESS;

  constructor(public payload: string) { }
}

export class StartCheckingSession implements Action {
  readonly type = GameActionsTypes.START_CHECKING_SESSION;

  constructor(public payload: string) { }
}

export class StartNewGameSuccess implements Action {
  readonly type = GameActionsTypes.START_NEW_GAME_SUCCESS;
}

export class WatchGame implements Action {
  readonly type = GameActionsTypes.WATCH_GAME;
}

export class GameOver implements Action {
  readonly type = GameActionsTypes.GAME_OVER;
}

export type GameActions =
  GameOver |
  StartCheckingSession |
  WaitForOtherPlayersSuccess |
  SavePlayerId |
  WatchGame |
  WaitForOtherPlayers |
  StartNewGame |
  StartNewGameSuccess
  ;
