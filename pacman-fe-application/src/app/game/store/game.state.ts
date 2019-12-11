import { Observable } from "rxjs";
import { Session } from "src/app/models/session";
import { SocketMessage } from "src/app/models/socket-messages";

export enum Mode { WATCH, PLAY, NONE}

export interface GameState {
  activeSession: Session;
  mode: Mode;
  playerId: string;
  activeCheckSessionSocket: Observable<SocketMessage>;
  loading: boolean;
}

export const initialGameState: GameState = {
  activeSession: null,
  mode: Mode.NONE,
  playerId: null,
  activeCheckSessionSocket: null,
  loading: false,
};

