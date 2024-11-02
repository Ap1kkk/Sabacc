import { classNames } from '@/shared/lib/classNames/classNames';
import { memo, ReactNode } from 'react';
import cls from './Card.module.scss';

interface CardProps {
  className?: string;
  children: ReactNode;
  type: '1' | '2' | '3' | '4';
}

export const Card = memo((props: CardProps) => {
  const { className, children, ...otherProps } = props;

  return (
    <div className={classNames(cls.Card, {}, [className])} {...otherProps}>
      {children}
    </div>
  );
});
