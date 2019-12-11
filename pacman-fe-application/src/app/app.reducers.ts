import * as fromAuth from "src/app/auth/store/auth.reducers";
import * as fromGame from "src/app/game/store/game.reducers";

export const reducers = {
  gameState: fromGame.reducer,
  authState: fromAuth.reducer,
};
