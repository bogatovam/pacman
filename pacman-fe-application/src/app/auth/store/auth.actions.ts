import { Action } from "@ngrx/store";

export enum AuthActionsTypes {
  LOGIN = "[Auth] Login"
}


export class Login implements Action {
  readonly type = AuthActionsTypes.LOGIN;
}

 export type AuthActions = Login;
