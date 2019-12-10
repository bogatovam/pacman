import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { MatButtonModule } from "@angular/material";
import { EffectsModule } from "@ngrx/effects";
import { StoreModule } from "@ngrx/store";
import { GameRoutingModule } from "src/app/game/game-routing.module";
import { GameEffects } from "src/app/game/store/game.effects";
import * as fromGame from "src/app/game/store/game.reducers";
import { HttpErrorInterceptor } from "src/app/services/error.interceptor";
import { GamePanelComponent } from './components/game-panel/game-panel.component';
import { GameComponent } from './components/game/game.component';
@NgModule({
  declarations: [GameComponent, GamePanelComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    MatButtonModule,
    GameRoutingModule,
    StoreModule.forFeature("game", fromGame.reducer),
    EffectsModule.forFeature([GameEffects]),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    },
  ],
})
export class GameModule { }
