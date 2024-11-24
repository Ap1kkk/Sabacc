import { memo, useCallback, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { Client, Message } from '@stomp/stompjs';
import { classNames } from '@/shared/lib/classNames/classNames';
import { useWebSocketSubscription } from '@/shared/lib/hooks/useWebSocketSubscription';
import { selectCurrentUser } from '@/features/Auth';
import { GameHeader } from '../GameHeader/GameHeader';
import { GameFooter } from '../GameFooter/GameFooter';
import { Card, GameState, TurnType } from '../../model/types/game';
import cls from './Game.module.scss';
import { useOpponent } from '@/shared/lib/hooks/useOpponent';
import { RoomState } from '@/entities/Room/types/room';
import { GameCard, GameCardType } from '@/entities/GameCard';
import { i } from 'node_modules/vite/dist/node/types.d-aGj9QkWt';
import { GameCardModal } from '../GameCardModal/GameCardModal';
import { GameBank } from '../GameBank/GameBank';

interface GameProps {
  client: Client;
  gameState: GameState;
  roomState: RoomState;
}

export const Game = memo(({ client, gameState, roomState }: GameProps) => {
  const user = useSelector(selectCurrentUser);
  const opponent = useOpponent(user?.id, roomState);
  const [modalCards, setModalCads] = useState<{ cards: Card[], type: GameCardType } | null>(null)

  const sendTurn = useCallback((turnType: string, details: object = {}) => {
    if (client && user) {
      console.log({
        sessionId: roomState.id,
        playerId: user.id,
        turnType,
        details: {
          ...details
        }
      })
      
      client.publish({
        destination: `/app/input/session/${roomState.id}/turn`,
        body: JSON.stringify({
          sessionId: roomState.id,
          playerId: user.id,
          turnType,
          details: {
            ...details
          }
        }),
      })
    }
  },
    [client, user, roomState]
  );


  const handleGetSand = () => {
    sendTurn(TurnType.GET_SAND)
  }
  const handleGetDiscardSand = () => {
    sendTurn(TurnType.GET_SAND_DISCARD)
  }
  const handleGetBlood = () => {
    sendTurn(TurnType.GET_BLOOD)
  }
  const handleGetDiscardBlood = () => {
    sendTurn(TurnType.GET_BLOOD_DISCARD)
  }

  useEffect(() => {
    const playerIndex = gameState.players[0].playerId == user?.id ? 0 : 1;
    if (gameState.players[playerIndex].bloodCards.length > 1) {
      setModalCads({ cards: gameState.players[playerIndex].bloodCards, type: GameCardType.BLOOD })
    } else if (gameState.players[playerIndex].sandCards.length > 1) {
      setModalCads({ cards: gameState.players[playerIndex].sandCards, type: GameCardType.SAND })
    } else {
      setModalCads(null)
    }
  }, [gameState])

  return (
    <>
      {modalCards && <GameCardModal cards={modalCards.cards} sendTurn={sendTurn} type={modalCards.type} />}

      <div className={classNames(cls.ç, {}, [])}>
        <GameHeader opponent={opponent} isCurentTurn={opponent?.id === gameState.currentPlayerId} gameState={gameState} />

        <div className={cls.table}>
          <div className={cls.discardBlood}>
            {gameState.bloodDiscard && <GameCard type={GameCardType.BLOOD} card={gameState.bloodDiscard} onClick={handleGetDiscardBlood} />}
          </div>
          <GameCard type={GameCardType.BLOOD} isFlipped isMultiple onClick={handleGetBlood} />
          <GameCard type={GameCardType.SAND} isFlipped isMultiple onClick={handleGetSand} />
          <div className={cls.discardSand}>
            {gameState.sandDiscard && <GameCard type={GameCardType.SAND} card={gameState.sandDiscard} onClick={handleGetDiscardSand} />}
          </div>

          <GameBank gameState={gameState} userId={user?.id} className={cls.bank} sendTurn={sendTurn} />
        </div>

        <GameFooter user={user!} isCurentTurn={user?.id === gameState.currentPlayerId} gameState={gameState} sendTurn={sendTurn} />
      </div>
    </>
  );
});

export default Game;
