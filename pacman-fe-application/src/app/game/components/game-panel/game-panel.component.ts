import { Component, OnInit } from '@angular/core';
import { Store } from "@ngrx/store";
import { AppState } from "src/app/app.state";
import { StartNewGame } from "src/app/game/store/game.actions";

@Component({
  selector: 'game-panel',
  templateUrl: './game-panel.component.html',
  styleUrls: ['./game-panel.component.css']
})
export class GamePanelComponent implements OnInit {
  constructor(private store: Store<AppState>) { }

  ngOnInit(): void {
  }

  startGame(): void {
    console.log("clicks");
    this.store.dispatch(new StartNewGame());
  }
}
