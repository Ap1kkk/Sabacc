import { classNames, Mods } from '@/shared/lib/classNames/classNames';
import { memo } from 'react';
import cls from './GameFooter.module.scss';
import { User } from '@/features/Auth/model/types/auth';
import { GameTokens } from '../GamePlayerControls/GameTokens';

interface GameFooterProps {
  user: User;
  isCurentTurn: boolean
}

export const GameFooter = memo((props: GameFooterProps) => {
  const { user, isCurentTurn, ...otherProps } = props;

  const mods: Mods = {
    [cls.currentUserTurn]: isCurentTurn
  }

  return (
    <div className={classNames(cls.footer, {}, [])}>
      <GameTokens userId={user?.id} />

      <h5 className={classNames(cls.nickname, mods, [])}>{user?.username || 'Opponent'}</h5>
    </div>
  );
});
