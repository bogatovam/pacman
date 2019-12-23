import { User } from "src/app/auth/models/user";
import { SessionGameState } from "src/app/models/session-game-state";

export interface Session {
  id: string;
  gameState?: SessionGameState;
  players?: User[];
  watchers?: User[];
}
