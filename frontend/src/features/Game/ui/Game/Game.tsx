
import { memo, useEffect } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import cls from './Game.module.scss'

export const Game = memo((sendTurn: any, gameState: any, players: any) => {
  const [playerOne, playerTwo] = players || [];

  useEffect(() => {
    console.log(playerOne, playerTwo)
    console.log(gameState)
  }, [])

  return (
    <>
      <div className={classNames(cls.header, {}, [])}>
        <h5 className={classNames(cls.nickname, {}, [])}>{playerOne?.username || 'Player 1'}</h5>
      </div>

      <div className={classNames(cls.table, {}, [])}>
        {/* Отображение текущего состояния игры */}
        <pre>{JSON.stringify(gameState, null, 2)}</pre>

        {/* Кнопки для ходов */}
        <button onClick={() => sendTurn('PASS')}>Спасовать</button>
        <button onClick={() => sendTurn('GET_SAND')}>Взять песок</button>
        <button onClick={() => sendTurn('DISCARD_SAND', { index: 0 })}>Сбросить песок</button>
        <button onClick={() => sendTurn('GET_BLOOD')}>Взять кровь</button>
        <button onClick={() => sendTurn('DISCARD_BLOOD', { index: 0 })}>Сбросить кровь</button>
      </div>

      <div className={classNames(cls.footer, {}, [])}>
        <h5 className={classNames(cls.nickname, {}, [])}>{playerTwo?.username || 'Player 2'}</h5>
      </div>
    </>
  );
});

export default Game;
