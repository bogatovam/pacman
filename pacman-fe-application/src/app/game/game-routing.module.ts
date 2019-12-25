import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { GamePanelComponent } from "src/app/game/components/game-panel/game-panel.component";
import { GameComponent } from "src/app/game/components/game/game.component";

const GAME_ROUTES = [
  {
    path: 'game-panel', component: GamePanelComponent,
  }, {
    path: 'game', component: GameComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(GAME_ROUTES)
  ],
  exports: [RouterModule]
})
export class GameRoutingModule {
}
