import { Injectable } from '@angular/core';
import { GAME_HEIGHT, GAME_WIDTH } from "src/app/game/services/consts";
import { DrawService } from "src/app/game/services/draw.service";
import { CellType } from "src/app/models/cell-type";
import { Color } from "src/app/models/color";
import { GameObject } from "src/app/models/game-object";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";

@Injectable({
  providedIn: 'root'
})
export class DeltaResolverService {
  resolveBoard(ctx: CanvasRenderingContext2D, prevBoard: CellType[][], currBoard: CellType[][]): void {

    if (!prevBoard && currBoard) {
      this.drawService.initContext(ctx, currBoard);
    } else if (prevBoard && currBoard) {
      for (let x = 0; x < GAME_WIDTH; x++) {
        for (let y = 0; y < GAME_HEIGHT; y++) {
          if (prevBoard[y][x] !== currBoard[y][x]) {
            this.drawService.drawLabyrinthField(ctx, x, y, currBoard[y][x]);
          }
        }
      }
    }
  }

  resolvePacmans(ctx: CanvasRenderingContext2D, prevPacmans: Pacman[], currPacmans: Pacman[]): void {
    if (!prevPacmans && currPacmans) {
      for (let i = 0; i < currPacmans.length; i++) {
        this.drawService.initPacman(ctx, currPacmans[i], this.getEntityCoordinates(currPacmans[i]));
      }
    } else if (prevPacmans && currPacmans) {

    }
  }

  resolveGhosts(ctx: CanvasRenderingContext2D, prevGhosts: Ghost[], currGhosts: Ghost[]): void {
    if (!prevGhosts && currGhosts) {
      for (let i = 0; i < currGhosts.length; i++) {
        switch (currGhosts[i].color) {
          case Color.BLUE: {
            console.log(currGhosts[i].color);

            this.drawService.initBlueGhost(ctx, currGhosts[i], this.getEntityCoordinates(currGhosts[i]));
            break;
          }
          case Color.PINK: {
            this.drawService.initPinkGhost(ctx, currGhosts[i], this.getEntityCoordinates(currGhosts[i]));
            break;
          }
          case Color.RED: {
            this.drawService.initRedGhost(ctx, currGhosts[i], this.getEntityCoordinates(currGhosts[i]));
            break;
          }
          case Color.YELLOW: {
            console.log(this.getEntityCoordinates(currGhosts[i]));
            this.drawService.initYellowGhost(ctx, currGhosts[i], this.getEntityCoordinates(currGhosts[i]));
            break;
          }
        }
      }
    } else if (prevGhosts && currGhosts) {

    }
  }

  private getEntityCoordinates(entity: GameObject): { x: number, y: number, offsetX: number, offsetY: number } {
    return {
      y: Math.round(entity.coords.x), x: Math.round(entity.coords.y),
      offsetY: entity.coords.x - Math.round(entity.coords.x),
      offsetX: entity.coords.y - Math.round(entity.coords.y)
    };
  }

  constructor(
    private  drawService: DrawService,
  ) {
  }
}
