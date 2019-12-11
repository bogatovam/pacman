import { createFeatureSelector, createSelector } from "@ngrx/store";
import { AuthState } from "src/app/auth/store/auth.state";


export const selectGameState = createFeatureSelector('auth');
export const retrieveUserId = createSelector(selectGameState, (state: AuthState) => state.user && state.user.id);
