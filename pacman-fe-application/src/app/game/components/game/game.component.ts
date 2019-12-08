import { Component, OnInit } from '@angular/core';
import { Store } from "@ngrx/store";
import { AppState } from "src/app/app.state";
import { MessageFromEffect, MessageFromReducer } from "src/app/game/store/game.actions";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  constructor(private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.store.dispatch(new MessageFromReducer());
    this.store.dispatch(new MessageFromEffect());
  }

}
