import { memo } from 'react';
import cls from './BackgroundImg.module.scss';
import MainImg from '@/shared/assets/images/main.jpg'

export const BackgroundImg = memo(() => {
  return (
    <img className={cls.img} src={MainImg} />
  );
});
