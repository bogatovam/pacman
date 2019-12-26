import { Component, OnInit } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
import { User } from "src/app/auth/models/user";
import { BLOCK_SIZE, ZOOM } from "src/app/game/services/consts";
import { GameStoreService } from "src/app/game/services/game-store.service";
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
  mode$: Observable<Mode>;

  loading$: Observable<boolean> = null;
  isActiveSessionPresent$: Observable<boolean> = null;
  activeSessions$: Observable<SimpleMap<{ players: User[], countWatchers: number }>> = null;

  constructor(private store$: Store<AppState>,
              private  gameUtilService: GameUtilService,
              private  gameStoreService: GameStoreService,
  ) {
  }

  ngOnInit(): void {
    this.activeSessions$ = this.gameUtilService.activeSessions$;
    this.isActiveSessionPresent$ = this.gameStoreService.isActiveSessionPresent();
    this.loading$ = this.gameStoreService.isLoading();
    this.mode$ = this.gameStoreService.getMode();
  }

  startGame(): void {
    this.store$.dispatch(new StartNewGame());
  }

  waitForOtherPlayers(sessionId: string): void {
    this.store$.dispatch(new WatchGame(sessionId));
  }

  showWatchGameMenu(): void {
    this.gameStoreService.setMode(Mode.WATCH);
  }
}
