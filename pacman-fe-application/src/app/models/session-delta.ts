import { Session } from "src/app/models/session";

export interface SessionDelta {
  session: Session;
  action?: SessionDeltaAction;
}

export enum SessionDeltaAction { CHANGE = 'CHANGE', REMOVE = 'REMOVE', NONE = 'NONE' }
