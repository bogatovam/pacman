import { Injectable } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
import { retrieveUserId } from "src/app/game/store/game.selectors";

@Injectable({
  providedIn: 'root'
})
export class GameStoreService {

  constructor(private store: Store<AppState>) { }

  retrieveUserId(): Observable<string> {
    return this.store.select(retrieveUserId);
  }
}
