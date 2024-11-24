import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '@/features/Auth/model/selectors/authSelector';
import { useWebSocketGame } from '@/shared/lib/hooks/useWebSocketGame';
import { useSetupRoom } from '@/shared/lib/hooks/useSetupRoom';
import axios from 'axios';
import { GameState, GameStatus } from '../types/game';

export const useGameState = () => {
  const playerId = useSelector(selectCurrentUser)?.id;
  const sessionId = playerId && useSetupRoom(playerId);
  const client = useWebSocketGame(playerId, sessionId);

  const [roomState, setRoomState] = useState<any>();
  const [gameState, setGameState] = useState<GameState | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const fetchGameState = async () => {
    if (!sessionId) return;
    try {
      const data = (
        await axios.get(`${__API__}/api/v1/room/game/current-state?sessionId=${sessionId}`)
      ).data;
      setGameState(data);
      return data;
    } catch (err) {
      setTimeout(() => fetchGameState(), 500);
    }
  };

  const fetchRoomState = async () => {
    if (!sessionId) return;
    const data = (await axios.get(`${__API__}/api/v1/room/${sessionId}`)).data;
    setRoomState(data);
  };

  useEffect(() => {
    if (client && sessionId) {
      const handleConnect = () => {
        fetchRoomState();
        fetchGameState();
        client.subscribe(`/queue/session/${sessionId}/game-progress`, (message) => {
          fetchGameState();
          fetchRoomState();
        });
        client.subscribe(`/queue/session/${sessionId}/accepted-turns`, (message) => {
          fetchGameState();
          fetchRoomState();
        });
      };

      client.onConnect = handleConnect;
      setIsLoading(false);
    }
  }, [client, sessionId]);

  const isGameInProgress = () => {
    const status = roomState?.status;
    return (
      status !== GameStatus.WAITING_SECOND_USER &&
      status !== GameStatus.PLAYER_DISCONNECTED &&
      status !== GameStatus.FINISHED
    );
  };

  return {
    client,
    gameState,
    roomState,
    isLoading,
    isGameInProgress: isGameInProgress(),
    fetchGameState,
  };
};
