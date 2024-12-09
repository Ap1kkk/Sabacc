import { classNames } from '@/shared/lib/classNames/classNames';
import { memo, ReactNode, useEffect } from 'react';
import cls from './GameRoundResultModal.module.scss';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '@/features/Auth';
import { useOpponent } from '@/shared/lib/hooks/useOpponent';
import CreditImg from '@/shared/assets/images/credit.png'
import BackgroundTable from '@/shared/assets/images/table_cubes.png'

interface GameRoundResultModalProps {
  roundResult: any;
  roomState: any;
  onClose: () => void;
}

export const GameRoundResultModal = memo(({ roundResult, roomState, onClose }: GameRoundResultModalProps) => {
  const user = useSelector(selectCurrentUser);
  const opponent = useOpponent(user?.id, roomState);

  const getUsernameById = {
    [user!.id]: user?.username,
    [opponent!.id]: opponent?.username
  }

  useEffect(() => {
    console.log(roundResult)

    return () => {

    }
  }, [])

  return (
    <div className={cls.GameRoundResultModal} onClick={onClose}>
      <div className={cls.modalСontent}>
        <img src={BackgroundTable} className={cls.background} />
        <h1>Результаты раунда {roundResult.round}</h1>
        <ul className={cls.list}>
          {roundResult.players.map((player: any) => (
            <li key={player.playerId} className={cls.container}>
              <h4 className={cls.nickname}>
                <span>{getUsernameById[player.playerId]}</span>
              </h4>

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
