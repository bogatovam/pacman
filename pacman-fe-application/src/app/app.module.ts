import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Router, RouterModule } from "@angular/router";
import { EffectsModule } from "@ngrx/effects";
import { StoreModule } from "@ngrx/store";
import { AppRoutingModule } from "src/app/app-routing.module";
import { reducers } from "src/app/app.reducers";
import { AuthModule } from "src/app/auth/auth.module";
import { GameModule } from "src/app/game/game.module";
import { SocketService } from "src/app/services/socket.service";
import { NotFoundComponent } from "src/app/util-components/not-found/not-found.component";

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule,
    AuthModule,
    GameModule,
    AppRoutingModule,
    StoreModule.forRoot(reducers),
    EffectsModule.forRoot([]),
  ],
  providers: [SocketService],
  bootstrap: [AppComponent]
})
export class AppModule { }
