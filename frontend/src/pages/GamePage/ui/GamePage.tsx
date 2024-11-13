import React, { useEffect, useState, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '@/features/Auth/model/selectors/authSelector';
import axios from 'axios';
import Game from '@/features/Game/ui/Game/Game';
import { classNames } from '@/shared/lib/classNames/classNames';
import cls from './GamePage.module.scss';
import { useWebSocket } from '@/shared/lib/hooks/useWebSocketGame';

const GamePage = () => {
  const playerId = useSelector(selectCurrentUser)?.id
  const [players, setPlayers] = useState<any[]>()
  const [sessionId, setSessionId] = useState<number>();
  const [gameState, setGameState] = useState();
  const client = useWebSocket(playerId, sessionId)
  const [started, setStarted] = useState<any>()

  useEffect(() => {
    const leaveAllRooms = async () => {
      localStorage.removeItem('roomId');
      try {
        await axios.post(`${__API__}/api/v1/room/leave/all?userId=${playerId}`);
      } catch (error) {
        console.error('Ошибка при выходе из комнат:', error);
      }
    };


    const setupRoomAndWebSocket = async () => {
      let roomId = localStorage.getItem('roomId');

      if (roomId) {
        const data = (await axios.get(`${__API__}/api/v1/room/${roomId}`)).data;
        if (data.status !== 'FINISHED' ?? (data.playerFirst.id === playerId || data.playerSecond.id === playerId)) {
          setSessionId(+roomId)
        } else {
          leaveAllRooms();
          roomId = null;
        }
      }

      if (!roomId) {
        const availableRoomsResponse = await axios.get(`${__API__}/api/v1/room/available-for-join?userId=${playerId}`);
        const availableRooms = availableRoomsResponse.data;

        if (availableRooms.length > 0) {
          roomId = availableRooms[0].id;
          await axios.post(`${__API__}/api/v1/room/${roomId}/join?userId=${playerId}`);
          setStarted('STARTED')
        } else {
          const roomResponse = await axios.post(`${__API__}/api/v1/room/create?userId=${playerId}`);
          roomId = roomResponse.data.id;
        }
      }

      if (roomId) {
        localStorage.setItem('roomId', roomId);
        setSessionId(+roomId);
      }
    };

    if (playerId) {
      setupRoomAndWebSocket();
    }
  }, [playerId]);

  const fetchGameState = async () => {
    if (!sessionId) return;
    try {
      const gameStateResponse = await axios.get(`${__API__}/api/v1/room/game/current-state?sessionId=${sessionId}`);
      const data = JSON.parse(gameStateResponse.data ?? '{}')
      if (data.status && data.status === 'STARTED') {
        setPlayers([data.details.playerFirst, data.details.playerSecond]);
      }
      console.log(players)
    } catch (error) {
      console.error('Ошибка при получении состояния игры:', error);
    }
  };

  //const client = useWebSocket(playerId, sessionId)

  useEffect(() => {
    if (client && sessionId) {
      const handleConnect = () => {
        client.subscribe(`/queue/session/${sessionId}/game-progress`, (message) => {
          console.log('Получено сообщение из WebSocket /game-progress:', JSON.parse(message.body));
          setStarted(JSON.parse(message.body).status)
          //setOpponent(JSON.parse(message.body).sessionRoom.playerSecond)
          //fetchGameState();
        });
      };

      client.onConnect = handleConnect;
    }
  }, [client, sessionId, fetchGameState]);

  const sendTurn = useCallback((turnType, details = {}) => {
    if (!client || !sessionId) return;

    client.publish({
      destination: `/app/game/${sessionId}/turn`,
      body: JSON.stringify({ playerId, turnType, details }),
    });
  }, [client, sessionId, playerId]);

  return (
    <div className={classNames(cls.game, {}, [])}>
      {(started !== 'STARTED' && started !== 'PLAYER_RECONNECTED') ? (
        <div className={classNames(cls.loader, {}, [])}>Ожидание соперника...</div>
      ) : (
        <Game sendTurn={sendTurn} gameState={gameState} players={players} />
      )}
    </div>
  );
};

export default GamePage;
