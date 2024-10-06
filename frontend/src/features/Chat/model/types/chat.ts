import { User } from "@/features/Auth/model/types/auth";

export interface Chat {
  id: number;
  members: User[];
}