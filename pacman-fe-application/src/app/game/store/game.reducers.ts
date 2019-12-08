import { GameActions, GameActionsTypes } from "src/app/game/store/game.actions";
import { GameState, initialGameState } from "src/app/game/store/game.store";

export function reducer(state: GameState = initialGameState, action: GameActions): GameState {
  switch (action.type) {
    case GameActionsTypes.START_NEW_GAME: {
      return {
        ...state,
        loading: true,
      };
    }

    case GameActionsTypes.START_NEW_GAME_SUCCESS: {
      return {
        ...state,
        playerId: action.payload,
        loading: false,
      };
    }

    default:
      return state;
  }
}
