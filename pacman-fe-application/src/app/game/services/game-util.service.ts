import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { User } from "src/app/auth/models/user";
import { GameRestService } from "src/app/game/services/game-rest.service";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { Session } from "src/app/models/session";
import { SimpleMap } from "src/app/models/simple-map";

@Injectable({
  providedIn: 'root'
})
export class GameUtilService {

  public activeSessions$: Observable<SimpleMap<{ players: User[], countWatchers: number }>> =
    this.gameRestService.getAllActiveSessions().pipe(
      map((sessions: Session[]) => {
          const res: SimpleMap<{ players: User[], countWatchers: number }> = {};
          sessions.forEach(session => {
            res[session.id] = { players: session.players, countWatchers: session.watchers.length};
          });
          return res;
        }
      ),
    );

  constructor(private  gameRestService: GameRestService,
              private  gameStoreService: GameStoreService,
  ) { }
}
