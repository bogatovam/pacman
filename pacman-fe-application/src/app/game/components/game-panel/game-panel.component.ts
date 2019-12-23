import { Component, OnInit } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
import { User } from "src/app/auth/models/user";
import { GameUtilService } from "src/app/game/services/game-util.service";
import { StartNewGame, WatchGame } from "src/app/game/store/game.actions";
import { Mode } from "src/app/game/store/game.state";
import { SimpleMap } from "src/app/models/simple-map";

@Component({
  selector: 'game-panel',
  templateUrl: './game-panel.component.html',
  styleUrls: ['./game-panel.component.css']
})
export class GamePanelComponent implements OnInit {
  Mode = Mode;
  isActiveSessionPresent: boolean = false;
  isLoading: boolean = false;
  mode: Mode = Mode.NONE;
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

  showWatchGameMenu(): void {
    this.mode = Mode.WATCH;
  }
}
