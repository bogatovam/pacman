import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { filter, first, mergeMap, pairwise, switchMap, tap } from "rxjs/operators";
import { AppState } from "src/app/app.state";
import { User } from "src/app/auth/models/user";
import { KEY } from "src/app/game/services/consts";
import { DeltaResolverService } from "src/app/game/services/delta-resolver.service";
import { DrawService } from "src/app/game/services/draw.service";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { GameUtilService } from "src/app/game/services/game-util.service";
import { DoPlayerAction } from "src/app/game/store/game.actions";
import { Mode } from "src/app/game/store/game.state";
import { CellType } from "src/app/models/cell-type";
import { Ghost } from "src/app/models/ghost";
import { Pacman } from "src/app/models/pacman";
import { SessionDeltaAction } from "src/app/models/session-delta";


@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  constructor(private store$: Store<AppState>,
              private  gameStoreService: GameStoreService,
              private  drawService: DrawService,
              private  deltaResolver: DeltaResolverService,
              private  gameUtilService: GameUtilService,
  ) {
  }

  SessionDeltaAction = SessionDeltaAction;
  Mode = Mode;
  @ViewChild('board', {static: true})
  canvas: ElementRef<HTMLCanvasElement>;

  mode$: Observable<Mode>;
  ctx: CanvasRenderingContext2D;
  level$: Observable<number>;
  score$: Observable<number>;
  livesCount$: Observable<number>;

  playersDictionary$: Observable<Map<User, Pacman>>;
  watchers$: Observable<User[]>;

  gameStatus$: Observable<SessionDeltaAction>;

  timer: { mins: number, secs: number } = {mins: 0, secs: 0};

  moves = {
    [KEY.LEFT]: (): void => this.store$.dispatch(new DoPlayerAction({x: 0.0, y: -1.0})),
    [KEY.RIGHT]: (): void => this.store$.dispatch(new DoPlayerAction({x: 0.0, y: 1.0})),
    [KEY.DOWN]: (): void => this.store$.dispatch(new DoPlayerAction({x: 1.0, y: 0.0})),
    [KEY.UP]: (): void => this.store$.dispatch(new DoPlayerAction({x: -1.0, y: 0.0})),
    [KEY.NONE]: (): void => this.store$.dispatch(new DoPlayerAction({x: 0.0, y: 0.0}))
  };

  press = {
    [KEY.LEFT]: false,
    [KEY.RIGHT]: false,
    [KEY.DOWN]: false,
    [KEY.UP]: false,
    [KEY.NONE]: false
  };


  @HostListener('window:keydown', ['$event'])
  keyDownEvent(event: KeyboardEvent): void {
    if (this.moves[event.keyCode] && !this.press[event.keyCode]) {
      console.log((event));
      this.press[event.keyCode] = true;
      this.moves[event.keyCode]();
    }
  }

  @HostListener('window:keyup', ['$event'])
  keyUpEvent(event: KeyboardEvent): void {
    if (this.moves[event.keyCode] && this.press[event.keyCode]) {
      this.press[event.keyCode] = false;
      this.moves[KEY.NONE]();
    }
  }

  ngOnInit(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d');
    this.level$ = this.gameStoreService.getLevel();
    this.gameStatus$ = this.gameStoreService.getGameStatus();
    this.watchers$ = this.gameStoreService.getWatchers();

    this.score$ = this.gameUtilService.score$;
    this.livesCount$ = this.gameUtilService.livesCount$;
    this.playersDictionary$ = this.gameUtilService.playersDictionary$;
    this.mode$ = this.gameStoreService.getMode();

    this.gameStoreService.getGameBoard().pipe(
      pairwise(),
    ).subscribe(([oldBoard, newBoard]: [CellType[][], CellType[][]]) => {
      this.deltaResolver.resolveBoard(this.ctx, oldBoard, newBoard);
    });


    this.gameStoreService.getPacmans().pipe(
      pairwise(),
    ).subscribe(([prev, curr]: [Pacman[], Pacman[]]) => {
      this.deltaResolver.resolvePacmans(this.ctx, prev, curr);
    });


    this.gameStoreService.getGhosts().pipe(
      pairwise(),
    ).subscribe(([prev, curr]: [Ghost[], Ghost[]]) => {
      this.deltaResolver.resolveGhosts(this.ctx, prev, curr);
    });

    this.gameStoreService.getTime().pipe(
      filter((time) => !!time),
      first(),
      tap((time) => {
        const initSec: number = Math.floor((time / 1000) % 60);
        const initMin: number = Math.floor((time / 1000) / 60);
        this.timer.secs = initSec;
        this.timer.mins = initMin;
        setInterval(() => {
          if (this.timer.secs + 1 >= 60) {
            this.timer.secs = 0;
            this.timer.mins++;
          } else {
            this.timer.secs++;
          }
        }, 1000);
      }),
    ).subscribe();
  }
}

