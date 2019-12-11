import { User } from "src/app/auth/models/user";
import { Color } from "src/app/models/color";
import { GameObject } from "src/app/models/game-object";

export interface Pacman extends GameObject {
  lifeCount?: number;
  score?: number;
  color?: Color;
  user?: User;
}

