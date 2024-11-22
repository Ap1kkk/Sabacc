export enum GameStatus {
  STARTED = 'STARTED',
  PLAYER_RECONNECTED = 'PLAYER_RECONNECTED',
  ALL_USERS_JOINED = 'ALL_USERS_JOINED',
  ALL_USERS_CONNECTED = 'ALL_USERS_CONNECTED',
  IN_PROGRESS = 'IN_PROGRESS',
  WAITING_SECOND_USER = 'WAITING_SECOND_USER',
  PLAYER_DISCONNECTED = 'PLAYER_DISCONNECTED',
  FINISHED = 'FINISHED',
}

export interface GameState {
  currentPlayerId: number;
  round: number;
  bloodDiscard: Card | null;
  sandDiscard: Card | null;
  players: Player[];
}

export interface Player {
  playerId: number;
  tokens: string[];
  remainChips: number;
  spentChips: number;
  bloodCards: Card[];
  sandCards: Card[];
  handRating: HandRating;
}

export interface Card {
  cardValueType: string;
  value?: number;
}

export interface HandRating {
  first: number;
  second: number;
}

