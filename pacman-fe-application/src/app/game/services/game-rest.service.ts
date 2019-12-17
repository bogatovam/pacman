import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { User } from "src/app/auth/models/user";
import { Session } from "src/app/models/session";
import { routes } from "src/app/routes";


@Injectable({
  providedIn: 'root'
})
export class GameRestService {

  startNewGame(userId: string): Observable<string> {
    return this.http.get<string>(routes.newGame + userId );
  }

  getAllActiveSessions(): Observable<Session> {
    return this.http.get<Session>(routes.getAllSession);
  }

  connectToSession(sessionId: string, userId: string): Observable<User> {
    return this.http.get<User>(routes.connectToSession + sessionId + "/user/" + userId);
  }

  constructor(private http: HttpClient) { }
}
