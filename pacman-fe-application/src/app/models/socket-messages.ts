import { SessionDelta } from "src/app/models/session-delta";

export type SocketMessage = string | {} | SessionDelta | { sessionId: string; };
