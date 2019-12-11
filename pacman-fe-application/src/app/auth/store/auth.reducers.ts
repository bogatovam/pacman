import { AuthActions } from "src/app/auth/store/auth.actions";
import { AuthState, initialAuthState } from "src/app/auth/store/auth.state";

export function reducer(state: AuthState = initialAuthState, action: AuthActions): AuthState {
  switch (action.type) {
    default:
      return state;
  }
}
