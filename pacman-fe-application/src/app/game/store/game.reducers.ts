import { GameActions, GameActionsTypes } from "src/app/game/store/game.actions";
import { GameState, initialGameState } from "src/app/game/store/game.store";

export function reducer(state: GameState = initialGameState, action: GameActions): GameState {
  switch (action.type) {
    case GameActionsTypes.GET_MESSAGE_FROM_EFFECT: {
      console.log("effect");
      return state;
    }

    case GameActionsTypes.GET_MESSAGE_FROM_REDUCER: {
      console.log("reducer");
      return state;
    }
    default:
      return state;
  }
}
