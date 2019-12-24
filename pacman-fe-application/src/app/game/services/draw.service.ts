import { Injectable } from '@angular/core';
import { BLOCK_SIZE, GAME_HEIGHT, GAME_WIDTH, ZOOM } from "src/app/game/services/consts";
import { CellType } from "src/app/models/cell-type";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";

@Injectable({
  providedIn: 'root'
})
export class DrawService {

  constructor() {
  }

  initContext(ctx: CanvasRenderingContext2D, board?: CellType[][]): void {
    ctx.canvas.width = GAME_WIDTH * BLOCK_SIZE * ZOOM;
    ctx.canvas.height = GAME_HEIGHT * BLOCK_SIZE * ZOOM;
    this.buildLabyrinth(ctx, board);
  }

  buildLabyrinth(ctx: CanvasRenderingContext2D, board: CellType[][]): void {
    ctx.lineCap = "round";
    for (let x = 0; x < GAME_WIDTH; x++) {
      for (let y = 0; y < GAME_HEIGHT; y++) {
        if (board[y][x] === CellType.WALL) {
          ctx.fillStyle = "rgb(9,9,11)";

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

  drawLabyrinthField(ctx: CanvasRenderingContext2D, x: number, y: number, type: CellType): void {
    console.log("change")
    ctx.clearRect(x * BLOCK_SIZE * ZOOM, y * BLOCK_SIZE * ZOOM,
      BLOCK_SIZE * ZOOM, BLOCK_SIZE * ZOOM);
    if (type === CellType.WALL) {
      ctx.fillStyle = "rgb(9,9,11)";

      ctx.fillRect(x * BLOCK_SIZE * ZOOM, y * BLOCK_SIZE * ZOOM,
        BLOCK_SIZE * ZOOM, BLOCK_SIZE * ZOOM);
    } else if (type === CellType.SCORE) {
      ctx.fillStyle = "rgb(246,253,2)";
      ctx.fillRect(x * BLOCK_SIZE * ZOOM + 5, y * BLOCK_SIZE * ZOOM + 5,
        2, 2);
    }
  }

  drawPacman(ctx: CanvasRenderingContext2D,
             prevPacman: Pacman,
             prevEntityCoordinates: { x: number; y: number; offsetX: number; offsetY: number },
             currPacman: Pacman,
             currEntityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {

  }

  initPacman(ctx: CanvasRenderingContext2D,
             currPacman: Pacman,
             entityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    const image = new Image(60, 60);
    image.src = './assets/pacman.png';

    setTimeout(e => ctx.drawImage(image, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 60, 60), 1000);
  }

  initRedGhost(ctx: CanvasRenderingContext2D,
               currPacman: Ghost,
               entityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    const image = new Image(60, 60);
    image.src = './assets/red.png';
    setTimeout(e => ctx.drawImage(image, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 60, 60), 1000);
  }

  initPinkGhost(ctx: CanvasRenderingContext2D,
                currPacman: Ghost,
                entityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    const image = new Image(60, 60);
    image.src = './assets/pink.png';
    setTimeout(e => ctx.drawImage(image, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 60, 60), 1000);
  }

  initYellowGhost(ctx: CanvasRenderingContext2D,
                  currPacman: Ghost,
                  entityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    const image = new Image(60, 60);
    image.src = './assets/orange.png';
    setTimeout(e => ctx.drawImage(image, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 60, 60), 1000);
  }

  initBlueGhost(ctx: CanvasRenderingContext2D,
                currPacman: Ghost,
                entityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    const image = new Image(60, 60);
    image.src = './assets/blue.png';
    setTimeout(e => ctx.drawImage(image, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 60, 60), 1000);
  }

}
