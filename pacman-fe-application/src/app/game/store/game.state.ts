import { User } from "src/app/auth/models/user";
import { CellType } from "src/app/models/cell-type";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";
import { SessionDeltaAction } from "src/app/models/session-delta";

export enum Mode { WATCH, PLAY, NONE}

export interface GameState {
  mode: Mode;
  gameId: string;
  playerId: string;
  activeSessionId: string;
  gameStatus: SessionDeltaAction;
  time: number;
  level: number ;
  pacmans: Pacman[];
  ghosts: Ghost[];
  cellMatrix: CellType[][];
  players: User[];
  watchers: User[];
  loading: boolean;
}

export const initialGameState: GameState = {
  mode: Mode.NONE,
  playerId: null,
  activeSessionId: null,
  gameId: null,
  gameStatus: SessionDeltaAction.NONE,
  time: 0,
  level: 0,
  pacmans: null,
  ghosts: null,
  cellMatrix: null,
  players: null,
  watchers: null,
  loading: false,
};

