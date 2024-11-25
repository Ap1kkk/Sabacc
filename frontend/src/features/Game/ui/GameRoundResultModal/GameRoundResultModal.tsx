import { classNames } from '@/shared/lib/classNames/classNames';
import { memo, ReactNode, useEffect } from 'react';
import cls from './GameRoundResultModal.module.scss';

interface GameRoundResultModalProps {
  roundResult: any;
  onClose: () => void;
}

export const GameRoundResultModal = memo(({ roundResult, onClose }: GameRoundResultModalProps) => {
  useEffect(() => {
    console.log(roundResult)
  }, [roundResult])
  
  return (
    <div className={cls.GameRoundResultModal} onClick={onClose}>
      <div className={cls.modalСontent}>
        <h1>Результаты раунда</h1>
        <ul>
          {roundResult.players.map((player: any) => (
            <li key={player.playerId}>
              Игрок {player.playerId}: {player.remainChips} фишек (потрачено: {player.spentChips})
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
});
