import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Store } from "@ngrx/store";
import { AppState } from "src/app/app.state";
import { BLOCK_SIZE, GAME_HEIGHT, GAME_WIDTH } from "src/app/game/services/consts";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { GameUtilService } from "src/app/game/services/game-util.service";

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  @ViewChild('board', { static: true })
  canvas: ElementRef<HTMLCanvasElement>;

  ctx: CanvasRenderingContext2D;

  constructor(private store$: Store<AppState>,
              private  gameUtilService: GameUtilService,
              private  gameStoreService: GameStoreService,
  ) {
  }
  ngOnInit(): void {
    this.gameStoreService.getGameBoard().subscribe((_) => console.log(_));
    this.initBoard();
  }

  initBoard(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d');

    this.ctx.canvas.width = GAME_WIDTH * BLOCK_SIZE;
    this.ctx.canvas.height = GAME_HEIGHT * BLOCK_SIZE;
  }
}
