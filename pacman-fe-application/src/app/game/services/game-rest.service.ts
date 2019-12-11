import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { routes } from "src/app/routes";


@Injectable({
  providedIn: 'root'
})
export class GameRestService {

  startNewGame(userId: string): Observable<string> {
    return this.http.get<string>(routes.newGame + userId );
  }

  constructor(private http: HttpClient) { }
}
