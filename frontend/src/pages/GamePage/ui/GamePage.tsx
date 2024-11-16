import React, { useEffect, useState, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '@/features/Auth/model/selectors/authSelector';
import Game from '@/features/Game/ui/Game/Game';
import { classNames } from '@/shared/lib/classNames/classNames';
import cls from './GamePage.module.scss';
import { useWebSocketGame } from '@/shared/lib/hooks/useWebSocketGame';
import { useSetupRoom } from '@/shared/lib/hooks/useSetupRoom';
import axios from 'axios';

interface RoomState {

}

enum GameStatus {
  // Показываю игру
  STARTED = 'STARTED',
  PLAYER_RECONNECTED = 'PLAYER_RECONNECTED',
  ALL_USERS_JOINED = 'ALL_USERS_JOINED',
  ALL_USERS_CONNECTED = 'ALL_USERS_CONNECTED',
  IN_PROGRESS = 'IN_PROGRESS',

  // Нет
  WAITING_SECOND_USER = 'WAITING_SECOND_USER',
  PLAYER_DISCONNECTED = 'PLAYER_DISCONNECTED',
  FINISHED = 'FINISHED'
}

const GamePage = () => {
  const playerId = useSelector(selectCurrentUser)?.id;
  const sessionId = playerId && useSetupRoom(playerId);
  const client = useWebSocketGame(playerId, sessionId);

  const [roomState, setRoomState] = useState<any>()
  const [gameState, setGameState] = useState<any>()


  const fetchGameState = async () => {
    if (!sessionId) return;
    const data = (await axios.get(`${__API__}/api/v1/room/game/current-state?sessionId=${sessionId}`)).data;
    setGameState(data);
    //   {
    //     "currentPlayerId": 1,
    //     "round": 1,
    //     "bloodDiscard": null,
    //     "sandDiscard": null,
    //     "players": [
    //         {
    //             "tokens": [
    //                 "NO_TAX",
    //                 "TAKE_TWO_CHIPS",
    //                 "OTHER_PLAYERS_PAY_ONE"
    //             ],
    //             "remainChips": 4,
    //             "spentChips": 0,
    //             "bloodCards": [
    //                 {
    //                     "value": 6,
    //                     "cardValueType": "VALUE_CARD"
    //                 }
    //             ],
    //             "sandCards": [
    //                 {
    //                     "cardValueType": "IMPOSTER"
    //                 }
    //             ],
    //             "handRating": null
    //         },
    //         {
    //             "tokens": [
    //                 "NO_TAX",
    //                 "TAKE_TWO_CHIPS",
    //                 "OTHER_PLAYERS_PAY_ONE"
    //             ],
    //             "remainChips": 4,
    //             "spentChips": 0,
    //             "bloodCards": [
    //                 {
    //                     "value": 1,
    //                     "cardValueType": "VALUE_CARD"
    //                 }
    //             ],
    //             "sandCards": [
    //                 {
    //                     "value": 6,
    //                     "cardValueType": "VALUE_CARD"
    //                 }
    //             ],
    //             "handRating": null
    //         }
    //     ]
    // }
  };

  const fetchRoomState = async () => {
    if (!sessionId) return;
    const data = (await axios.get(`${__API__}/api/v1/room/${sessionId}`)).data;
    setRoomState(data)
  }

  useEffect(() => {
    if (client && sessionId) {
      const handleConnect = () => {
        fetchRoomState();
        fetchGameState();

        client.subscribe(`/queue/session/${sessionId}/game-progress`, (message) => {
          fetchGameState();
          fetchRoomState();
        });
      };

      client.onConnect = handleConnect;
    }
  }, [client, sessionId]);

  const gameInProcess = (status: GameStatus): boolean => {
    console.log(!(
      status !== GameStatus.WAITING_SECOND_USER &&
      status !== GameStatus.PLAYER_DISCONNECTED &&
      status !== GameStatus.FINISHED
    ));
    return (
      status !== GameStatus.WAITING_SECOND_USER &&
      status !== GameStatus.PLAYER_DISCONNECTED &&
      status !== GameStatus.FINISHED
    );
  };

  useEffect(() => {
    console.log('STATE ROOM:')
    console.log(roomState)
    console.log('STATE GAME:')
    console.log(gameState)
  }, [roomState, gameState])

  if (!gameState || !roomState) return (
    <div className={classNames(cls.game, {}, [])}>
      <div>нет состояния</div>
    </div>
  )

  return (
    <div className={classNames(cls.game, {}, [])}>
      {(!gameInProcess(roomState.status)) ? (
        <div className={classNames(cls.loader, {}, [])}>Ожидание соперника...</div>
      ) : (
        <Game client={client} gameState={gameState} roomState={roomState} />
      )}
    </div>
  );
};

export default GamePage;
