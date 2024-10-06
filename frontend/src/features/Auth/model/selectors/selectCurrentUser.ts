import { AuthSchema } from "../types/authSchema";

export const selectCurrentUser = (state: AuthSchema) => state.user;