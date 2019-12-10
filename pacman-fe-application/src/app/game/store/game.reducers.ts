import { GameActions, GameActionsTypes } from "src/app/game/store/game.actions";
import { GameState, initialGameState } from "src/app/game/store/game.state";

export function reducer(state: GameState = initialGameState, action: GameActions): GameState {
  switch (action.type) {
    case GameActionsTypes.START_NEW_GAME: {
      return {
        ...state,
        loading: true,
      };
    }

    case GameActionsTypes.SAVE_PLAYER_ID: {
      return {
        ...state,
        playerId: action.payload,
      };
    }

    case GameActionsTypes.WAIT_FOR_OTHER_PLAYERS_SUCCESS: {
      return {
        ...state,
        activeSession: {id: action.payload},
      };
    }

    case GameActionsTypes.START_NEW_GAME_SUCCESS: {
      return {
        ...state,
        loading: false,
      };
    }

    default:
      return state;
  }
}
