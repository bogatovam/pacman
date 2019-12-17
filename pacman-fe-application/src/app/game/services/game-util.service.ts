import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { User } from "src/app/auth/models/user";
import { GameRestService } from "src/app/game/services/game-rest.service";
import { Session } from "src/app/models/session";
import { SimpleMap } from "src/app/models/simple-map";

@Injectable({
  providedIn: 'root'
})
export class GameUtilService {

  // #TODO refactor:
  public activeSessions$: Observable<SimpleMap<User[]>> = this.gameRestService.getAllActiveSessions().pipe(
    map((session: Session) => {
        const id: string = session.id;
        return {id: session.players};
      }
    ),
  );

  constructor(private  gameRestService: GameRestService) { }
}
