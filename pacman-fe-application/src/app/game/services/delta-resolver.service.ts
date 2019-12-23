import { Injectable } from '@angular/core';
import { Action } from "@ngrx/store";
import { SessionDelta } from "src/app/models/session-delta";

@Injectable({
  providedIn: 'root'
})
export class DeltaResolverService {

  resolve(state: SessionDelta): Action[] {
    console.log("I RESOLVE SOMETH");
    return [];
  }

  constructor() { }
}
