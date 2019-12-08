
export interface GameState {
  loading: boolean;
  userId: string;
  playerId: string;
  message: string;
}
export const initialGameState: GameState = {
  loading: false,
  userId: Math.floor(Math.random() * 1000000).toString(),
  playerId: null,
  message: "Hello, i'm initial",
};
