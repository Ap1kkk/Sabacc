import { memo } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import token0 from '@/shared/assets/images/token_0.png';
import token1 from '@/shared/assets/images/token_-1.png';
import token2 from '@/shared/assets/images/token_+2.png';
import cls from './GameTokens.module.scss';
import { TokensTypes } from '../../model/types/game';

interface GameTokensProps {
  userId: number | undefined;
  tokens: TokensTypes[];
  isClickable?: boolean;
}

export const GameTokens = memo((props: GameTokensProps) => {
  const { userId, tokens, isClickable = false } = props;
  if (!userId || !tokens) return <div></div>

  return (
    <div className={classNames(cls.controlsContainer, {}, [])}>
      {tokens.includes(TokensTypes.NO_TAX) &&
        <button className={classNames(cls.cardButton, { [cls.clickable]: isClickable }, [])}><img src={token0} alt="Card back" /></button>
      }
      {tokens.includes(TokensTypes.TAKE_TWO_CHIPS) &&
        <button className={classNames(cls.cardButton, { [cls.clickable]: isClickable }, [])}><img src={token1} alt="Card back" /></button>
      }
      {tokens.includes(TokensTypes.OTHER_PLAYERS_PAY_ONE) &&
        <button className={classNames(cls.cardButton, { [cls.clickable]: isClickable }, [])}><img src={token2} alt="Card back" /></button>
      }
    </div >
  );
});

export default GameTokens;
