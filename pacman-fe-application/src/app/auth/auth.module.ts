import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EffectsModule } from "@ngrx/effects";
import { StoreModule } from "@ngrx/store";
import { AuthRoutingModule } from "src/app/auth/auth-routing.module";
import { AuthStoreService } from "src/app/auth/services/auth-store.service";
import { AuthEffects } from "src/app/auth/store/auth.effects";
import * as fromAuth from "src/app/auth/store/auth.reducers";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AuthRoutingModule,
    StoreModule.forFeature("auth", fromAuth.reducer),
    EffectsModule.forFeature([AuthEffects]),
  ],
  providers: [AuthStoreService]
})
export class AuthModule { }
