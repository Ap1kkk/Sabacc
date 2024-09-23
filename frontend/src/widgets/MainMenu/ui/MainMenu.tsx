import { memo } from 'react';
import cls from './MainMenu.module.scss'
import { AppLink } from '@/shared/ui/AppLink';
import { classNames } from '@/shared/lib/classNames/classNames';

interface MainMenuProps {
  className?: string;
}

export const MainMenu = memo((props: MainMenuProps) => {
  const { className } = props;
  return (
    <div className={classNames(cls.Menu, {}, [className])}>
      <AppLink variant='btn' fullWidth to={'game'}>Играть</AppLink>
      <AppLink variant='btn' fullWidth to={'game'}>Настройки</AppLink>
      <AppLink variant='btn' fullWidth to={'game'}>История игр</AppLink>
      <AppLink variant='btn' fullWidth to={'game'}>Аккаунт</AppLink>
      <AppLink variant='btn' fullWidth to={'game'}>Правила</AppLink>
    </div>
  );
});

export default MainMenu;
