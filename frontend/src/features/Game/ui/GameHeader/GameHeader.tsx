import { classNames, Mods } from '@/shared/lib/classNames/classNames';
import { memo } from 'react';
import cls from './GameHeader.module.scss';
import { User } from '@/features/Auth/model/types/auth';
import { GameState } from '../../model/types/game';
import GamePlayerControls, { GameTokens } from '../GamePlayerControls/GameTokens';

interface GameHeaderProps {
  gameState: GameState;
  opponent: User | null;
  isCurentTurn: boolean;
}

export const GameHeader = memo((props: GameHeaderProps) => {
  const { opponent, isCurentTurn, gameState, ...otherProps } = props;
  const i = gameState?.players[0].playerId == opponent?.id ? 0 : 1;

  const mods: Mods = {
    [cls.currentUserTurn]: isCurentTurn
  }

  return (
    <div className={classNames(cls.header, {}, [])}>
      <h5 className={classNames(cls.nickname, mods, [])}>{opponent?.username || 'Opponent'}</h5>

      <div className={cls.controls}>
        <GameTokens userId={opponent?.id}/>
      </div>

      {/*   <GameCard type={GameCardType.BLOOD} value={gameState.players[i].bloodCards[0].cardValueType} isFlipped />
      <GameCard type={GameCardType.SAND} value={gameState.players[i].sandCards[0].cardValueType} isFlipped /> */}
    </div>
  );
});
