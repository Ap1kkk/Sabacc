import { User } from "@/features/Auth/model/types/auth";

export interface Room {
  id: number;
  status: "WAITING_SECOND_USER" | "IN_PROGRESS" | "COMPLETED"; // Можно расширить по мере необходимости
  playerFirst: User | null;
  playerSecond: User | null;
  playerFirstConnected: boolean;
  playerSecondConnected: boolean;
}