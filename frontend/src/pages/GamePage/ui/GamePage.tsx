import { Client } from '@stomp/stompjs';
import { memo, useEffect, useState } from 'react';
import SockJS from 'sockjs-client';

export const GamePage = memo(() => {
  const [client, setClient] = useState<any>(null);

  useEffect(() => {
    // Создание SockJS-клиента и STOMP-клиента
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log('Соединение установлено');
      },
      onStompError: (error) => {
        console.error(`Ошибка STOMP: ${error.headers.message}`);
      },
    });

    // Активация STOMP-клиента
    stompClient.activate();
    setClient(stompClient);

    return () => {
      if (stompClient.active) {
        stompClient.deactivate();
      }
    };
  }, []);

  return (
    <div></div>
  );
});

export default GamePage;
