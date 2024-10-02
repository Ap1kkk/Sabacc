import { memo } from 'react';
import cls from './BackgroundImg.module.scss';

interface BackgroundImgProps {
  src: string;
}

export const BackgroundImg = memo((props: BackgroundImgProps) => {
  const { src, ...otherProps } = props;

  return (
    <img className={cls.img} src={src} />
  );
});
