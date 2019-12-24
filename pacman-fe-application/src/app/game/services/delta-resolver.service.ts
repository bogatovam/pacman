import { Injectable } from '@angular/core';
import { Action } from "@ngrx/store";
import { DrawService } from "src/app/game/services/draw.service";
import { CellType } from "src/app/models/cell-type";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";
import { SessionDelta } from "src/app/models/session-delta";

@Injectable({
  providedIn: 'root'
})
export class DeltaResolverService {

  resolve(state: SessionDelta): Action[] {
    return [];
  }

  resolveBoard(ctx: CanvasRenderingContext2D, prevBoard: CellType[][],  currBoard: CellType[][]): void {
    if (!prevBoard && currBoard) {
      this.drawService.initContext(ctx, currBoard);
    } else if (prevBoard && currBoard) {

    }
  }


  resolvePacmans(ctx: CanvasRenderingContext2D, prevPacmans: Pacman[],  currPacmans: Pacman[]): void {
    if (prevPacmans && currPacmans) {

    }
  }


  resolveGhosts(ctx: CanvasRenderingContext2D, prevGhosts: Ghost[],  currGhosts: Ghost[]): void {
    if (prevGhosts && currGhosts) {

    }
  }
  constructor(
    private  drawService: DrawService,
  ) {
  }
}
