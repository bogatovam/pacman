import { createFeatureSelector, createSelector } from "@ngrx/store";
import { GameState } from "src/app/game/store/game.state";


export const selectGameState = createFeatureSelector('game');
export const retrieveActiveSession = createSelector(selectGameState, (state: GameState) => state.activeSession);
export const isLoading = createSelector(selectGameState, (state: GameState) => state.loading);
export const isActiveSessionPresent = createSelector(selectGameState, (state: GameState) => !!state.activeSession);
export const retrieveCellMatrix = createSelector(selectGameState, (state: GameState) => state.activeSession
  && state.activeSession.gameState
  && state.activeSession.gameState.cellMatrix);

// export const retrieveUserId = createSelector(selectGameState, (state: GameState) => state.userId);
