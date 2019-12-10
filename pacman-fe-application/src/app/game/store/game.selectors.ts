import { createFeatureSelector, createSelector } from "@ngrx/store";
import { GameState } from "src/app/game/store/game.state";


export const selectGameState = createFeatureSelector('game');
// export const retrieveUserId = createSelector(selectGameState, (state: GameState) => state.userId);
