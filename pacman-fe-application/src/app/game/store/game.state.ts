import { Observable } from "rxjs";
import { Session } from "src/app/models/session";
import { SocketMessage } from "src/app/models/socket-messages";

export interface GameState {
  activeSession: Session;
  playerId: string;
  activeCheckSessionSocket: Observable<SocketMessage>;
  loading: boolean;
}

export const initialGameState: GameState = {
  activeSession: null,
  playerId: null,
  activeCheckSessionSocket: null,
  loading: false,
};
