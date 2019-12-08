import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { GamePanelComponent } from "src/app/game/components/game-panel/game-panel.component";

const GAME_ROUTES = [
  {
    path: 'game-panel', component: GamePanelComponent,
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
