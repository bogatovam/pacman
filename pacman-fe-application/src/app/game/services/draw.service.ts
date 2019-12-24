import { Injectable } from '@angular/core';
import { BLOCK_SIZE, GAME_HEIGHT, GAME_WIDTH, ZOOM } from "src/app/game/services/consts";
import { CellType } from "src/app/models/cell-type";

@Injectable({
  providedIn: 'root'
})
export class DrawService {

  constructor() {
  }

  initContext(ctx: CanvasRenderingContext2D, board?: CellType[][]): void {
    ctx.canvas.width = GAME_WIDTH * BLOCK_SIZE * ZOOM;
    ctx.canvas.height = GAME_HEIGHT * BLOCK_SIZE * ZOOM;
    this.buildLabyrinth(ctx, board ? board : this.createStandardCellMatrix());
  }

  buildLabyrinth(ctx: CanvasRenderingContext2D, board: CellType[][]): void {
    ctx.lineCap = "round";
    for (let x = 0; x < GAME_WIDTH; x++) {
      for (let y = 0; y < GAME_HEIGHT; y++) {
        if (board[y][x] === CellType.WALL) {
          ctx.fillRect(x * BLOCK_SIZE * ZOOM, y * BLOCK_SIZE * ZOOM,
            BLOCK_SIZE * ZOOM, BLOCK_SIZE * ZOOM);
        } else if (board[y][x] === CellType.SCORE) {
          ctx.fillStyle = "rgb(246,253,2)";
          ctx.fillRect(x * BLOCK_SIZE * ZOOM + 5, y * BLOCK_SIZE * ZOOM + 5,
            1, 1);
        }
      }
    }
  }

  private createStandardCellMatrix(): CellType[][] {
    const cellMatrix: CellType[][] = [];

    const templates: string[] = [];
    templates[0] = "WWWWWWWWWWWWWWWWWWWWWWWWWWWW";
    templates[1] = "WE***********WW***********EW";
    templates[2] = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
    templates[3] = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
    templates[4] = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
    templates[5] = "W**************************W";
    templates[6] = "W*WWWW*WW*WWWWWWWW*WW*WWWW*W";
    templates[7] = "W*WWWW*WW*WWWWWWWW*WW*WWWW*W";
    templates[8] = "W******WW****WW****WW******W";
    templates[9] = "WWWWWW*WWWWWEWWEWWWWW*WWWWWW";
    templates[10] = "EEEEEW*WWWWWEWWEWWWWW*WEEEEE";
    templates[11] = "EEEEEW*WWEEEEEEEEEEWW*WEEEEE";
    templates[12] = "EEEEEW*WWEPPPPPPPPEWW*WEEEEE";
    templates[13] = "EEEEEW*WWEPEEEEEEPEWW*WEEEEE";
    templates[14] = "EEEEEW*EEEPEEEEEEPEEE*WEEEEE";
    templates[15] = "EEEEEW*WWEPEEEEEEPEWW*WEEEEE";
    templates[16] = "EEEEEW*WWEPPPPPPPPEWW*WEEEEE";
    templates[17] = "EEEEEW*WWEEEEEEEEEEWW*WEEEEE";
    templates[18] = "EEEEEW*WW WWWWWWWW WW*WEEEEE";
    templates[19] = "WWWWWW*WW WWWWWWWW WW*WWWWWW";
    templates[20] = "W************WW************W";
    templates[21] = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
    templates[22] = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
    templates[23] = "W***WW****************WW***W";
    templates[24] = "WWW*WW*WW*WWWWWWWW*WW*WW*WWW";
    templates[25] = "WWW*WW*WW*WWWWWWWW*WW*WW*WWW";
    templates[26] = "W******WW****WW****WW******W";
    templates[27] = "W*WWWWWWWWWW*WW*WWWWWWWWWW*W";
    templates[28] = "W*WWWWWWWWWW*WW*WWWWWWWWWW*W";
    templates[29] = "WE************************EW";
    templates[30] = "WWWWWWWWWWWWWWWWWWWWWWWWWWWW";

    for (let i = 0; i < GAME_HEIGHT; i++) {
      cellMatrix[i] = this.createRowByTemplate(templates[i]);
    }

    return cellMatrix;
  }

  private createRowByTemplate(template: string): CellType[] {
    const cellRow: CellType[] = [];
    for (let i = 0; i < template.length; i++) {
      if (template[i] === 'W' || template[i] === 'P') {
        cellRow[i] = CellType.WALL;
      } else if (template[i] === '*') {
        cellRow[i] = CellType.SCORE;
      } else {
        cellRow[i] = CellType.EMPTY;
      }
    }
    return cellRow;
  }
}
