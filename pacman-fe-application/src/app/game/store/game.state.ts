import { CellType } from "src/app/models/cell-type";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";

export enum Mode { WATCH, PLAY, NONE}

export interface GameState {
  mode: Mode;
  gameId: string;
  playerId: string;
  activeSessionId: string;
  time: number;
  level: number ;
  pacmans: Pacman[];
  ghosts: Ghost[];
  cellMatrix: CellType[][];
  loading: boolean;
}

export const initialGameState: GameState = {
  mode: Mode.NONE,
  gameId: null,
  playerId: null,
  activeSessionId: null,
  time: 0,
  level: 0,
  pacmans: null,
  ghosts: null,
  cellMatrix: null,
  loading: false,
};

