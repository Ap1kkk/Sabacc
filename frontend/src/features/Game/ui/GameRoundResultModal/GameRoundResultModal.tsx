import { classNames } from '@/shared/lib/classNames/classNames';
import { memo, ReactNode, useEffect, useState } from 'react';
import cls from './GameRoundResultModal.module.scss';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '@/features/Auth';
import { useOpponent } from '@/shared/lib/hooks/useOpponent';
import CreditImg from '@/shared/assets/images/credit.png'
import BackgroundTable from '@/shared/assets/images/table_cubes.png'
import { GameCard, GameCardType } from '@/entities/GameCard';
import { Card } from '../../model/types/game';

interface GameRoundResultModalProps {
  roundResult: any;
  roomState: any;
  onClose: () => void;
}

export const GameRoundResultModal = memo(({ roundResult, roomState, onClose }: GameRoundResultModalProps) => {
  const user = useSelector(selectCurrentUser);
  const opponent = useOpponent(user?.id, roomState);
  const [winnerIndex, setWinnerIndex] = useState(0);

  const getUsernameById = {
    [user!.id]: user?.username,
    [opponent!.id]: opponent?.username
  }

  useEffect(() => {
    if (roundResult.players[0].playerId == user!.id) {
      roundResult.players = roundResult.players.reverse();
      setWinnerIndex(1);
    } else {
      setWinnerIndex(0);
    }
  }, [roundResult])

  return (
    <div className={cls.GameRoundResultModal} onClick={onClose}>
      <div className={cls.modalСontent}>
        <img src={BackgroundTable} className={cls.background} />
        <h1>Результаты раунда {roundResult.round}</h1>
        <ul className={cls.list}>
          {roundResult.players.map((player: any, index: number) => (
            <li key={player.playerId} className={classNames(cls.container, {[cls.winner]: winnerIndex === index}, [])}>
              {winnerIndex === index && <h2 className={cls.winnerTitle}>Победитель раунда</h2>}
              <div>
                <h4 className={cls.nickname}>
                  <span>{getUsernameById[player.playerId]}</span>
                </h4>
                <h5 className={cls.cardRes}>
                  <GameCard type={GameCardType.BLOOD} card={player.bloodCards[0]}></GameCard>
                  <GameCard type={GameCardType.SAND} card={player.sandCards[0]}></GameCard>
                </h5>
              </div>

              <div>
                <div className={cls.credit}>
                  <span>Начальный банк {player.spentChips + player.remainChips}</span>
                  <div className={cls.imgContainer}>
                    <img src={CreditImg} />
                  </div>
                </div>

                <div className={cls.credit}>
                  <span>Текущий банк {player.spentChips}</span>
                  <div className={cls.imgContainer}>
                    <img src={CreditImg} />
                  </div>
                </div>

                <div className={cls.credit}>
                  <span>ИТОГО: {0 - player.remainChips}</span>
                  <div className={cls.imgContainer}>
                    <img src={CreditImg} />
                  </div>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
});
