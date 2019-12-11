import { Color } from "src/app/models/color";
import { GameObject } from "src/app/models/game-object";

export interface Ghost extends GameObject {
  color?: Color;
}
