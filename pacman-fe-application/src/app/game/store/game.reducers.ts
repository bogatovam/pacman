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

    case GameActionsTypes.START_NEW_GAME_SUCCESS: {
      return {
        ...state,
        loading: false,
      };
    }

    case GameActionsTypes.SET_ACTIVE_SESSION_ID: {
      return {
        ...state,
        activeSessionId: action.payload,
      };
    }

    case GameActionsTypes.SET_MODE: {
      return {
        ...state,
        mode: action.payload
      };
    }

    case GameActionsTypes.UPDATE_STATE: {
      return {
        ...state,
        gameId: action.payload.gameState.id,
        time: action.payload.gameState.time,
        level: action.payload.gameState.level,
        pacmans: action.payload.gameState.pacman,
        ghosts: action.payload.gameState.ghosts,
        cellMatrix: { ...action.payload.gameState.cellMatrix},
        players: action.payload.players,
        watchers: action.payload.watchers,
      };
    }

    default:
      return state;
  }
}
