import { Action } from "@ngrx/store";

export enum GameActionsTypes {
  GET_MESSAGE_FROM_REDUCER = '[Game] get message from reducer',
  GET_MESSAGE_FROM_EFFECT = '[Game] get message from effect',
}

export class MessageFromReducer implements Action {
  readonly type = GameActionsTypes.GET_MESSAGE_FROM_REDUCER;
}

export class MessageFromEffect implements Action {
  readonly type = GameActionsTypes.GET_MESSAGE_FROM_EFFECT;
}

export type GameActions =
  MessageFromReducer |
  MessageFromEffect
;
