import { Component, OnInit } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { WebSocketSubject, WebSocketSubjectConfig } from "rxjs/webSocket";
import { AppState } from "src/app/app.state";
import { User } from "src/app/auth/models/user";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { GameUtilService } from "src/app/game/services/game-util.service";
import { StartNewGame, WatchGame } from "src/app/game/store/game.actions";
import { SimpleMap } from "src/app/models/simple-map";
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

  activeSessions$: Observable<SimpleMap<User[]>> = null;

  constructor(private store$: Store<AppState>,
              private  gameUtilService: GameUtilService,
  ) { }

  ngOnInit(): void {
    this.activeSessions$ = this.gameUtilService.activeSessions$;
  }

    startGame(): void {
    this.store$.dispatch(new StartNewGame());
  }

    waitForOtherPlayers(sessionId: string): void {
    this.store$.dispatch(new WatchGame(sessionId));
  }
}
