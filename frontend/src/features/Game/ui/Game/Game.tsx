import { memo, useCallback, useState } from 'react';
import { useSelector } from 'react-redux';
import { Client, Message } from '@stomp/stompjs';
import { classNames } from '@/shared/lib/classNames/classNames';
import { useWebSocketSubscription } from '@/shared/lib/hooks/useWebSocketSubscription';
import { selectCurrentUser } from '@/features/Auth';
import { GameHeader } from '../GameHeader/GameHeader';
import { GameFooter } from '../GameFooter/GameFooter';
import { GameState } from '../../model/types/game';
import cls from './Game.module.scss';
import { useOpponent } from '@/shared/lib/hooks/useOpponent';
import { RoomState } from '@/entities/Room/types/room';
import { GameCard, GameCardType } from '@/entities/GameCard';

interface GameProps {
  client: Client;
  gameState: GameState;
  roomState: RoomState;
}

export const Game = memo(({ client, gameState, roomState }: GameProps) => {
  const user = useSelector(selectCurrentUser);
  const opponent = useOpponent(user?.id, roomState);

  useWebSocketSubscription(client, `/queue/session/${roomState.id}/accepted-turns`, (message: Message) => {
    const data = JSON.parse(message.body);
    console.log(data)
  });

  const sendTurn = useCallback(
    (turnType: string, details: object = {}) => {
      if (client && user) {
        client.publish({
          destination: `/app/input/session/${roomState.id}/turn`,
          body: JSON.stringify({
            sessionId: roomState.id,
            playerId: user.id,
            turnType,
            ...details,
          }),
        });
      }
    },
    [client, user, roomState]
  );

  return (
    <div className={classNames(cls.gameContainer, {}, [])}>
      <GameHeader opponent={opponent} isCurentTurn={opponent?.id === gameState.currentPlayerId} gameState={gameState} />
      <div className={cls.table}>

        {gameState.bloodDiscard ?
          <GameCard type={GameCardType.BLOOD} value={gameState.bloodDiscard.value} /> :
          <div></div>
        }
        <GameCard type={GameCardType.BLOOD} isFlipped isMultiple />
        <GameCard type={GameCardType.SAND} isFlipped isMultiple />
        
        {gameState.sandDiscard ?
          <GameCard type={GameCardType.BLOOD} value={gameState.sandDiscard.value} /> :
          <div></div>
        }
      </div>
      <GameFooter user={user!} isCurentTurn={user?.id === gameState.currentPlayerId} />
    </div>
  );
});

export default Game;
