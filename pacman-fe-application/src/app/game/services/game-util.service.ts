import { Injectable } from '@angular/core';
import { combineLatest, Observable } from "rxjs";
import { filter, map, withLatestFrom } from "rxjs/operators";
import { User } from "src/app/auth/models/user";
import { AuthStoreService } from "src/app/auth/services/auth-store.service";
import { GameRestService } from "src/app/game/services/game-rest.service";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { retrieveLevel } from "src/app/game/store/game.selectors";
import { Pacman } from "src/app/models/pacman";
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
            res[session.id] = {players: session.players, countWatchers: session.watchers.length};
          });
          return res;
        }
      ),
    );
  public playersDictionary$: Observable<Map<User, Pacman>> =
    combineLatest(
      this.gameStoreService.getPlayers(),
      this.gameStoreService.getPacmans(),
    ).pipe(
      filter(([players, pacmans]: [User[], Pacman[]]) => !!players && !!pacmans),
      map(([players, pacmans]: [User[], Pacman[]]) => {
        const res: Map<User, Pacman> = new Map<User, Pacman>();
        players.forEach((player: User) => {
          res.set(player, pacmans.find((pacman: Pacman) => pacman.user.id === player.id));
        });
        return res;
      }),
    );

  public score$: Observable<number> = combineLatest([
    this.gameStoreService.getPacmans(),
    this.authStoreService.retrieveUserId()]
  ).pipe(
    filter(([pacmans, userId]: [Pacman[], string]) => !!pacmans),
    map(([pacmans, userId]: [Pacman[], string]) =>
      pacmans.find((pacman: Pacman) => pacman.user.id === userId)),
    filter((pacman: Pacman) => !!pacman),

    map((pacman: Pacman) => pacman.score)
  );

  public livesCount$: Observable<number> = combineLatest([
    this.gameStoreService.getPacmans(),
    this.authStoreService.retrieveUserId()]
  ).pipe(
    filter(([pacmans, userId]: [Pacman[], string]) => !!pacmans),
    map(([pacmans, userId]: [Pacman[], string]) =>
      pacmans.find((pacman: Pacman) => pacman.user.id === userId)),
    filter((pacman: Pacman) => !!pacman),
    map((pacman: Pacman) => pacman.lifeCount)
  );

  constructor(private  gameRestService: GameRestService,
              private  gameStoreService: GameStoreService,
              private  authStoreService: AuthStoreService,
  ) {
  }
}
