import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

const AUTH_ROUTES = [
];

@NgModule({
  imports: [
    RouterModule.forChild(AUTH_ROUTES)
  ],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
