import { classNames, Mods } from '@/shared/lib/classNames/classNames';
import { memo } from 'react';
import cls from './GameHeader.module.scss';
import { User } from '@/features/Auth/model/types/auth';
import { GameState } from '../../model/types/game';
import { GameTokens } from '../GameTokens/GameTokens';
import { GameCard, GameCardType } from '@/entities/GameCard';

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
      <div className={classNames(cls.myCards)}>
        <GameCard type={GameCardType.BLOOD} isFlipped></GameCard>
        <GameCard type={GameCardType.SAND} isFlipped></GameCard>
      </div>


      <h5 className={classNames(cls.nickname, mods, [])}>{opponent?.username || 'Opponent'}</h5>

      <div className={cls.controls}>
        <GameTokens userId={opponent?.id} tokens={gameState.players[i].tokens} />
      </div>


    </div>
  );
});
