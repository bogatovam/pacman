import { createFeatureSelector, createSelector } from "@ngrx/store";
import { GameState } from "src/app/game/store/game.state";


export const selectGameState = createFeatureSelector('game');
export const isLoading = createSelector(selectGameState, (state: GameState) => state.loading);
export const isActiveSessionPresent = createSelector(selectGameState, (state: GameState) => !!state.activeSessionId);
export const retrieveCellMatrix = createSelector(selectGameState, (state: GameState) => state.cellMatrix);
export const retrievePacmans = createSelector(selectGameState, (state: GameState) => state.pacmans);
export const retrieveGhosts = createSelector(selectGameState, (state: GameState) => state.ghosts);
export const retrieveGameId = createSelector(selectGameState, (state: GameState) => state.gameId);
