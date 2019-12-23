import { Inject, Injectable } from '@angular/core';
import { interval, Observable, Observer, of, Subject, SubscriptionLike } from "rxjs";
import { distinctUntilChanged, filter, map, share, takeWhile, withLatestFrom } from "rxjs/operators";
import { WebSocketSubject, WebSocketSubjectConfig } from "rxjs/webSocket";
import { GameStoreService } from "src/app/game/services/game-store.service";
import { SocketMessage } from "src/app/models/socket-messages";
import { routes } from "src/app/routes";

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  public buildSocket(config: WebSocketSubjectConfig<SocketMessage | string>): Observable<SocketMessage | string> {
    return new WebSocketSubject(config).asObservable();
  }

  constructor() {}
  // private config: WebSocketSubjectConfig<SocketMessage<any>>;
  // private websocketSub: SubscriptionLike;
  // private statusSub: SubscriptionLike;
  // private reconnection$: Observable<number>;
  // private websocket$: WebSocketSubject<SocketMessage<any>>;
  // private connection$: Observer<boolean>;
  // private wsMessages$: Subject<SocketMessage<any>>;
  // private reconnectInterval: number;
  // private reconnectAttempts: number;
  // private isConnected: boolean;
  // public status: Observable<boolean>;
  // constructor(
  //             private  gameStoreService: GameStoreService,
  // ) {
  //   this.wsMessages$ = new Subject<SocketMessage<any>>();
  //   this.reconnectInterval = 5000; // pause between connections
  //   this.reconnectAttempts = 10; // number of connection attempts
  //   this.gameStoreService.retrieveUserId().subscribe((userId) => {
  //     this.config = {
  //       url: userId,
  //       closeObserver: {
  //         next: (event: CloseEvent) => {
  //           this.websocket$ = null;
  //           this.connection$.next(false);
  //         }
  //       },
  //       openObserver: {
  //         next: (event: Event) => {
  //           console.log('WebSocket connected!');
  //           this.connection$.next(true);
  //         }
  //       }
  //     };
  //   });
  //   // connection status
  //   this.status = new Observable<boolean>((observer) => {
  //     this.connection$ = observer;
  //   }).pipe(share(), distinctUntilChanged());
//
  //   // run reconnect if not connection
  //   this.statusSub = this.status
  //     .subscribe((isConnected) => {
  //       this.isConnected = isConnected;
//
  //       if (!this.reconnection$ && typeof(isConnected) === 'boolean' && !isConnected) {
  //         this.reconnect();
  //       }
  //     });
//
  //   this.websocketSub = this.wsMessages$.subscribe(
  //     null, (error: ErrorEvent) => console.error('WebSocket error!', error)
  //   );
//
  //   this.connect();
  // }
//
  // ngOnDestroy(): void {
  //   this.websocketSub.unsubscribe();
  //   this.statusSub.unsubscribe();
  // }
//
  // /*
  // * connect to WebSocked
  // * */
  // private connect(): void {
  //   this.websocket$ = new WebSocketSubject(this.config);
//
  //   this.websocket$.subscribe(
  //     (message) => this.wsMessages$.next(message),
  //     (error: Event) => {
  //       if (!this.websocket$) {
  //         // run reconnect if errors
  //         this.reconnect();
  //       }
  //     });
  // }
//
//
  // /*
  // * reconnect if not connecting or errors
  // * */
  // private reconnect(): void {
  //   console.log("reconnect");
  //   this.reconnection$ = interval(this.reconnectInterval)
  //     .pipe(takeWhile((v, index) => index < this.reconnectAttempts && !this.websocket$));
//
  //   this.reconnection$.subscribe(
  //     () => this.connect(),
  //     null,
  //     () => {
  //       // Subject complete if reconnect attemts ending
  //       this.reconnection$ = null;
//
  //       if (!this.websocket$) {
  //         this.wsMessages$.complete();
  //         this.connection$.complete();
  //       }
  //     });
  // }
//
//
  // /*
  // * on message event
  // * */
  // public on<T>(event: string): Observable<T> {
  //   if (event) {
  //     return this.wsMessages$.pipe(
  //       filter((message: SocketMessage<T>) => !!message),
  //       map((message: SocketMessage<T>) => message.data)
  //     );
  //   }
  // }
//
//
  // /*
  // * on message to server
  // * */
  // public send(event: string, data: any = {}): void {
  //   if (event && this.isConnected) {
  //     this.websocket$.next(<any>JSON.stringify({ event, data }));
  //   } else {
  //     console.error('Send error!');
  //   }
  // }
}
