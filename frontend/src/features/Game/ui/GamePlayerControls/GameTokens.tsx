import { memo } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import token0 from '@/shared/assets/images/token_0.png';
import token1 from '@/shared/assets/images/token_-1.png';
import token2 from '@/shared/assets/images/token_+2.png';
import cls from './GameTokens.module.scss';

interface GameTokensProps {
  userId: number | undefined;
}

export const GameTokens = memo((props: GameTokensProps) => {
  const { userId } = props;
  if (!userId) return <div></div>

  return (
    <div className={classNames(cls.controlsContainer, {}, [])}>
      <button className={cls.cardButton}><img src={token0} alt="Card back" /></button>
      <button className={cls.cardButton}><img src={token1} alt="Card back" /></button>
      <button className={cls.cardButton}><img src={token2} alt="Card back" /></button>
    </div>
  );
});

export default GameTokens;
