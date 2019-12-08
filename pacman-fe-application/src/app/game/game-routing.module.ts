import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { GameComponent } from "src/app/game/components/game/game.component";

const GAME_ROUTES = [
  {
    path: 'test', component: GameComponent,
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
