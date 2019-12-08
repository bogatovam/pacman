import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EffectsModule } from "@ngrx/effects";
import { StoreModule } from "@ngrx/store";
import { GameRoutingModule } from "src/app/game/game-routing.module";
import { GameEffects } from "src/app/game/store/game.effects";
import * as fromGame from "src/app/game/store/game.reducers";
import { GameComponent } from './components/game/game.component';
import { GamePanelComponent } from './components/game-panel/game-panel.component';

@NgModule({
  declarations: [GameComponent, GamePanelComponent],
  imports: [
    CommonModule,
    GameRoutingModule,
    StoreModule.forFeature("gameState", fromGame.reducer),
    EffectsModule.forFeature([GameEffects]),
  ],
})
export class GameModule { }
