import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Store } from "@ngrx/store";
import { AppState } from "src/app/app.state";

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  @ViewChild('board', { static: true })
  canvas: ElementRef<HTMLCanvasElement>;

  ctx: CanvasRenderingContext2D;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.initBoard();
  }

  initBoard(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d');

    this.ctx.canvas.width = COLS * BLOCK_SIZE;
    this.ctx.canvas.height = ROWS * BLOCK_SIZE;
  }
}
