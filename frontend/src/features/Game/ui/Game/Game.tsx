
import { memo, useCallback, useEffect, useState } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import cls from './Game.module.scss'
import { User } from '@/features/Auth/model/types/auth';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '@/features/Auth';
import axios from 'axios';

export const Game = memo(({ client, gameState, roomState }: any) => {
  const user = useSelector(selectCurrentUser);
  const [opponent, setOpponent] = useState<any>();

  const leaveAllRooms = async () => {
    if (user) await axios.post(`${__API__}/api/v1/room/leave/all?userId=${user.id}`);
  }
  useEffect(() => {
    if (!user || !roomState) return;
    const newOpponent = user.id == roomState.playerFirst.id ? roomState.playerSecond : roomState.playerFirst;
    setOpponent(newOpponent)

    return () => {
    //  leaveAllRooms();
    }
  }, [roomState])

  const sendTurn = useCallback(
    (turnType: string, details: object = {}) => {
      if (client && user) {
        client.publish({
          destination: `/input/game/${roomState.id}/turn`,
          body: JSON.stringify({ playerId: user.id, turnType, ...details }),
        });
      }
    },
    [client, user, roomState]
  );

  // if (!user || !opponent) return <div></div>

  return (
    <>
      <div className={classNames(cls.header, {}, [])}>
        <h5 className={classNames(cls.nickname, {}, [])}>{opponent?.username || 'Opponent'}</h5>
      </div>

      {/*   <div className={classNames(cls.table, {}, [])}>
        <pre>{JSON.stringify(gameState, null, 2)}</pre>

        <button onClick={() => sendTurn('PASS')}>Спасовать</button>
        <button onClick={() => sendTurn('GET_SAND')}>Взять песок</button>
        <button onClick={() => sendTurn('DISCARD_SAND', { index: 0 })}>Сбросить песок</button>
        <button onClick={() => sendTurn('GET_BLOOD')}>Взять кровь</button>
        <button onClick={() => sendTurn('DISCARD_BLOOD', { index: 0 })}>Сбросить кровь</button>
      </div> */}

      <div className={classNames(cls.footer, {}, [])}>
        <h5 className={classNames(cls.nickname, {}, [])}>{user?.username || 'Me'}</h5>
      </div>
    </>
  );
});

export default Game;
