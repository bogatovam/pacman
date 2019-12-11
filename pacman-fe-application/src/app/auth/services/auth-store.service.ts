import { Injectable } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
import { retrieveUserId } from "src/app/auth/store/auth.selectors";

@Injectable({
  providedIn: 'root'
})
export class AuthStoreService {

  constructor(private store: Store<AppState>) { }

  retrieveUserId(): Observable<string> {
    return this.store.select(retrieveUserId);
  }
}
