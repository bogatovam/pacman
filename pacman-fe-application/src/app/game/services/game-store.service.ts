import { Injectable } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
@Injectable({
  providedIn: 'root'
})
export class GameStoreService {

  constructor(private store: Store<AppState>) { }
}
