import { User } from "src/app/auth/models/user";

export interface AuthState {
  user: User;
}

export const initialAuthState: AuthState = {
  user: { id: Math.floor(Math.random() * 1000000).toString(), name: Math.floor(Math.random() * 1000000).toString()},
};
