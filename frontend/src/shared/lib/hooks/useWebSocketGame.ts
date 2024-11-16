import { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export const useWebSocketGame = (playerId: number | undefined, sessionId: number | undefined) => {
  const [client, setClient] = useState<Client | null>(null);

  useEffect(() => {
    if (!playerId || !sessionId) return;

    console.log('Создание сокета');
    const socket = new SockJS(`${__API__}/game?playerId=${playerId}&sessionId=${sessionId}`);
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onStompError: (error) => console.error(`Ошибка WebSocket: ${error}`),
    });

    stompClient.activate();
    setClient(stompClient);

    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, [playerId, sessionId]);

  return client;
};
