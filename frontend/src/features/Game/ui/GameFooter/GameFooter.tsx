import { classNames, Mods } from '@/shared/lib/classNames/classNames';
import { memo, useCallback, useEffect } from 'react';
import cls from './GameFooter.module.scss';
import RoolsImg from '@/shared/assets/images/rules.png'
import PassImg from '@/shared/assets/images/pass.png'
import GiveUpmg from '@/shared/assets/images/give_up.png'
import { User } from '@/features/Auth/model/types/auth';
import { GameTokens } from '../GameTokens/GameTokens';
import { AppLink } from '@/shared/ui';
import { getRouteRools } from '@/shared/const/router';
import { GameState, TurnType } from '../../model/types/game';
import { GameCard, GameCardType } from '@/entities/GameCard';

interface GameFooterProps {
  user: User;
  isCurentTurn: boolean;
  gameState: GameState;
  sendTurn: any;
}

export const GameFooter = memo((props: GameFooterProps) => {
  const { user, isCurentTurn, gameState, sendTurn, ...otherProps } = props;
  const i = gameState?.players[0].playerId == user?.id ? 0 : 1;

  const mods: Mods = {
    [cls.currentUserTurn]: isCurentTurn
  }

  return (
    <div className={classNames(cls.footer, {}, [])}>
      <div className={classNames(cls.myCards)}>
        <GameCard card={gameState.players[i].bloodCards[0]} type={GameCardType.BLOOD}></GameCard>
        <GameCard card={gameState.players[i].sandCards[0]} type={GameCardType.SAND}></GameCard>
      </div>


      <div className={cls.controls}>
        <GameTokens userId={user?.id} tokens={gameState.players[i].tokens} isClickable />
      </div>

      <button className={cls.button}><img src={GiveUpmg} alt="" /></button>
      <AppLink className={cls.button} to={getRouteRools()}><img src={RoolsImg} alt="" /></AppLink>
      <button className={cls.button}><img src={PassImg} alt="" /></button>

      <h5 className={classNames(cls.nickname, mods, [])}>{user?.username || 'Opponent'}</h5>
    </div>
  );
});
