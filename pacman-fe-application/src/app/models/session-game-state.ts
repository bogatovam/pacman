import { CellType } from "src/app/models/cell-type";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";

export interface SessionGameState {
  id: string;
  pacman?: Pacman[];
  ghosts?: Ghost[];
  cellMatrix?: CellType[][];
  level?: number ;
  time?: number;
}
