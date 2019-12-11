import { Component, OnInit } from '@angular/core';
import { Store } from "@ngrx/store";
import { WebSocketSubject, WebSocketSubjectConfig } from "rxjs/webSocket";
import { AppState } from "src/app/app.state";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { StartNewGame, WatchGame } from "src/app/game/store/game.actions";
import { SocketMessage } from "src/app/models/socket-messages";
import { routes } from "src/app/routes";
import { SocketService } from "src/app/services/socket.service";

@Component({
  selector: 'game-panel',
  templateUrl: './game-panel.component.html',
  styleUrls: ['./game-panel.component.css']
})
export class GamePanelComponent implements OnInit {
  isActiveSessionPresent: boolean = true;
  isLoading: boolean = false;

  constructor(private store$: Store<AppState>, private wsService: SocketService,
              private  gameStoreService: GameStoreService,
  ) { }

  ngOnInit(): void {

  }

  startGame(): void {
    this.store$.dispatch(new StartNewGame());
  }

  waitForOtherPlayers(): void {
    this.store$.dispatch(new WatchGame());
  }
}
