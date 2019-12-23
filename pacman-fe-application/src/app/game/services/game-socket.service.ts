import { Injectable } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "src/app/app.state";
import { GameOver, StartNewGameSuccess } from "src/app/game/store/game.actions";
import { SocketMessage } from "src/app/models/socket-messages";
import { routes } from "src/app/routes";
import { SocketService } from "src/app/services/socket.service";

@Injectable({
  providedIn: 'root'
})
export class GameSocketService {

  constructor(private socketService: SocketService, private store$: Store<AppState>) { }

  buildWaitingGameSocket(userId: string): Observable<SocketMessage | string> {
    return this.socketService.buildSocket({
        url: routes.wait + userId,
        closeObserver: {
          next: (event: CloseEvent) => {
            console.log("buildWaitingGameSocket close");
          }
        },
        openObserver: {
          next: (event: Event) => {
            console.log('buildWaitingGameSocket connected!');
          }
        }
      }
    );
  }

  buildCheckSessionSocket(sessionId: string): Observable<SocketMessage | string> {
    return this.socketService.buildSocket({
        url: routes.checkSession + sessionId,
        closeObserver: {
          next: (event: CloseEvent) => {
            this.store$.dispatch(new GameOver());
            console.log("buildCheckSessionSocket close");
          }
        },
        openObserver: {
          next: (event: Event) => {
            console.log('buildCheckSessionSocket connected!');
            this.store$.dispatch(new StartNewGameSuccess());
          }
        }
      }
    );
  }
}
