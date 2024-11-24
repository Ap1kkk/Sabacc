import { memo } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import token0 from '@/shared/assets/images/token_0.png';
import token1 from '@/shared/assets/images/token_-1.png';
import token2 from '@/shared/assets/images/token_+2.png';
import cls from './GameTokens.module.scss';
import { TokensTypes, TurnType } from '../../model/types/game';

interface GameTokensProps {
  userId: number | undefined;
  tokens: TokensTypes[];
  isClickable?: boolean;
  sendTurn?: any;
}

export const GameTokens = memo((props: GameTokensProps) => {
  const { userId, tokens, sendTurn, isClickable = false } = props;
  if (!userId || !tokens) return <div></div>

  {
    "sessionId": 13,
    "playerId": 14,
    "turnType": "PLAY_TOKEN",
    "details": {
      "token": "NO_TAX"
    }
  }

  const handleTurnToken = (typeToken: TokensTypes) => {
    sendTurn("PLAY_TOKEN", { token: typeToken })
  }

  return (
    <div className={classNames(cls.controlsContainer, {}, [])}>
      {tokens.includes(TokensTypes.NO_TAX) &&
        <button
          className={classNames(cls.cardButton, { [cls.clickable]: isClickable }, [])}
          onClick={() => handleTurnToken(TokensTypes.NO_TAX)}
        >
          <img src={token0} alt="Card back" />
        </button>
      }
      {tokens.includes(TokensTypes.TAKE_TWO_CHIPS) &&
        <button
          className={classNames(cls.cardButton, { [cls.clickable]: isClickable }, [])}
          onClick={() => handleTurnToken(TokensTypes.TAKE_TWO_CHIPS)}
        >
          <img src={token1} alt="Card back" />
        </button>
      }
      {tokens.includes(TokensTypes.OTHER_PLAYERS_PAY_ONE) &&
        <button
          className={classNames(cls.cardButton, { [cls.clickable]: isClickable }, [])}
          onClick={() => handleTurnToken(TokensTypes.OTHER_PLAYERS_PAY_ONE)}
        >
          <img src={token2} alt="Card back" />
        </button>
      }
    </div >
  );
});

export default GameTokens;
