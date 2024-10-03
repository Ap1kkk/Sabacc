import { User } from "./auth";

export interface AuthSchema {
  user: User | null;
  error: any;
}