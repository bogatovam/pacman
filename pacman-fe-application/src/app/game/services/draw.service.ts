import { Injectable } from '@angular/core';
import { BLOCK_SIZE, GAME_HEIGHT, GAME_WIDTH, ZOOM } from "src/app/game/services/consts";
import { CellType } from "src/app/models/cell-type";
import { Color } from "src/app/models/color";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";

@Injectable({
  providedIn: 'root'
})
export class DrawService {
  imagePacman = new Image(20, 20);
  imageRGhost = new Image(20, 20);
  imageBGhost = new Image(20, 20);
  imagePGhost = new Image(20, 20);
  imageYGhost = new Image(20, 20);


  constructor() {
    this.imagePacman.src = './assets/pacman.png';
    this.imageRGhost.src = "./assets/red.png";
    this.imageBGhost.src = "./assets/blue.png";
    this.imagePGhost.src = "./assets/pink.png";
    this.imageYGhost.src = "./assets/yellow.png";
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
  fillLabyrinthField(ctx: CanvasRenderingContext2D, x: number, y: number, type: CellType): void {
 if (type === CellType.SCORE) {
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
    ctx.clearRect(prevEntityCoordinates.x * BLOCK_SIZE * ZOOM + prevEntityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      prevEntityCoordinates.y * BLOCK_SIZE * ZOOM + prevEntityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 20, 20);
    this.initPacman(ctx, currPacman, currEntityCoordinates);
  }

  initPacman(ctx: CanvasRenderingContext2D,
             currPacman: Pacman,
             entityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    ctx.drawImage(this.imagePacman, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 20, 20);
  }

  drawGhost(ctx: CanvasRenderingContext2D,
            board: CellType[][],
            prevGhost: Ghost,
            prevEntityCoordinates: { x: number; y: number; offsetX: number; offsetY: number },
            currGhost: Ghost,
            currEntityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    ctx.clearRect(prevEntityCoordinates.x * BLOCK_SIZE * ZOOM + prevEntityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
      prevEntityCoordinates.y * BLOCK_SIZE * ZOOM + prevEntityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 20, 20);
    this.initGhost(ctx, currGhost, currEntityCoordinates);
    if (board) {
      this.fillLabyrinthField(ctx,
        prevEntityCoordinates.x,
        prevEntityCoordinates.y, board[prevEntityCoordinates.y][prevEntityCoordinates.x]);
      this.fillLabyrinthField(ctx,
        currEntityCoordinates.x,
        currEntityCoordinates.y, board[currEntityCoordinates.y][currEntityCoordinates.x]);
    }
  }

  initGhost(ctx: CanvasRenderingContext2D,
            currGhost: Ghost,
            entityCoordinates: { x: number; y: number; offsetX: number; offsetY: number }): void {
    switch (currGhost.color) {
      case Color.YELLOW: {
        ctx.drawImage(this.imageYGhost, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
          entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 20, 20);
        break;
      }
      case Color.RED: {
        ctx.drawImage(this.imageRGhost, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
          entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 20, 20);
        break;
      }
      case Color.BLUE: {
        ctx.drawImage(this.imageBGhost, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
          entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 20, 20);
        break;
      }
      case Color.PINK: {
        ctx.drawImage(this.imagePGhost, entityCoordinates.x * BLOCK_SIZE * ZOOM + entityCoordinates.offsetX * BLOCK_SIZE * ZOOM,
          entityCoordinates.y * BLOCK_SIZE * ZOOM + entityCoordinates.offsetY * BLOCK_SIZE * ZOOM, 20, 20);
        break;
      }
    }
  }
}
