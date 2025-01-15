import { classNames } from '@/shared/lib/classNames/classNames';
import { memo, ReactNode } from 'react';
import cls from './GameResultModal.module.scss';
import { useSelector } from 'react-redux';
import BackgroundTable from '@/shared/assets/images/table_cubes.png'
import { selectCurrentUser } from '@/features/Auth';
import { Button } from '@/shared/ui';

interface GameResultModalProps {
  winnerId: number;
  onClose: () => void;

}

export const GameResultModal = memo(({ winnerId, onClose }: GameResultModalProps) => {
  const user = useSelector(selectCurrentUser)

  if (!user) return <div></div>

  return (
    <div className={cls.GameResultModal}>
      <div className={cls.modalСontent}>
        <img src={BackgroundTable} className={cls.background} />
        <h1>Результаты игры</h1>
        <h2>{user.id == winnerId ? 'ПОБЕДА!' : "ПРОИГРЫШ("}</h2>
        <Button onClick={onClose} variant='btn'>Готово</Button>
      </div>
    </div>
  );
});
