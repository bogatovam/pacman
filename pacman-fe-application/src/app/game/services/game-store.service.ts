import { Injectable } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
import { User } from "src/app/auth/models/user";
import { isActiveSessionPresent, isLoading, retrieveCellMatrix, retrieveGameId, retrieveGameStatus, retrieveGhosts, retrieveLevel, retrievePacmans, retrievePlayers, retrieveWatchers } from "src/app/game/store/game.selectors";
import { CellType } from "src/app/models/cell-type";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";
import { SessionDeltaAction } from "src/app/models/session-delta";

@Injectable({
  providedIn: 'root'
})
export class GameStoreService {

  constructor(private store$: Store<AppState>) {
  }

  isLoading(): Observable<boolean> {
    return this.store$.select(isLoading);
  }

  isActiveSessionPresent(): Observable<boolean> {
    return this.store$.select(isActiveSessionPresent);
  }

  getGameBoard(): Observable<CellType[][]> {
    return this.store$.select(retrieveCellMatrix);
  }


  getPacmans(): Observable<Pacman[]> {
    return this.store$.select(retrievePacmans);
  }

  getGhosts(): Observable<Ghost[]> {
    return this.store$.select(retrieveGhosts);
  }

  getGameId(): Observable<string> {
    return this.store$.select(retrieveGameId);
  }
  getGameStatus(): Observable<SessionDeltaAction> {
    return this.store$.select(retrieveGameStatus);
  }

  getLevel(): Observable<number> {
    return this.store$.select(retrieveLevel);
  }

  getPlayers(): Observable<User[]> {
    return this.store$.select(retrievePlayers);
  }

  getWatchers(): Observable<User[]> {
    return this.store$.select(retrieveWatchers);
  }
}
