import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Store } from "@ngrx/store";
import { AppState } from "src/app/app.state";
import { BLOCK_SIZE, GAME_HEIGHT, GAME_WIDTH, ZOOM } from "src/app/game/services/consts";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { GameUtilService } from "src/app/game/services/game-util.service";
import { CellType } from "src/app/models/cell-type";

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  @ViewChild('board', {static: true})
  canvas: ElementRef<HTMLCanvasElement>;

  board: CellType[][];
  ctx: CanvasRenderingContext2D;

  constructor(private store$: Store<AppState>,
              private  gameUtilService: GameUtilService,
              private  gameStoreService: GameStoreService,
  ) {
  }

  ngOnInit(): void {
    //  this.gameStoreService.getGameBoard().subscribe((_) => console.log(_));
    this.initBoard();
  }

  initBoard(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d');

    this.ctx.canvas.width = GAME_WIDTH * BLOCK_SIZE *  ZOOM;
    this.ctx.canvas.height = GAME_HEIGHT * BLOCK_SIZE * ZOOM;
    this.board = this.createStandardCellMatrix();
    console.table(this.board);
    this.buildLabyrinth();
  }

  buildLabyrinth(): void {
    for (let x = 0; x < GAME_WIDTH ; x++) {
      for (let y = 0; y < GAME_HEIGHT; y++) {
        if (this.board[y][x] === CellType.WALL) {
          this.ctx.fillRect(x * BLOCK_SIZE * ZOOM, y * BLOCK_SIZE * ZOOM,
            BLOCK_SIZE * ZOOM, BLOCK_SIZE * ZOOM);
        }
      }
    }
  }

  createStandardCellMatrix(): CellType[][] {
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

  createRowByTemplate(template: string): CellType[] {
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
