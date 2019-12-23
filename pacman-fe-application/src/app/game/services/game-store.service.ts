import { Injectable } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
import { isActiveSessionPresent, isLoading, retrieveActiveSession } from "src/app/game/store/game.selectors";
import { Session } from "src/app/models/session";
@Injectable({
  providedIn: 'root'
})
export class GameStoreService {

  constructor(private store$: Store<AppState>) { }

  getActiveSession(): Observable<Session> {
    return this.store$.select(retrieveActiveSession);
  }

  isLoading(): Observable<boolean> {
    return this.store$.select(isLoading);
  }

  isActiveSessionPresent(): Observable<boolean> {
    return this.store$.select(isActiveSessionPresent);
  }
}
