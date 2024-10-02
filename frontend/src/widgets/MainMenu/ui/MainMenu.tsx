import { memo } from 'react';
import cls from './MainMenu.module.scss'
import { AppLink } from '@/shared/ui/AppLink';
import { classNames } from '@/shared/lib/classNames/classNames';
import { HStack, VStack } from '@/shared/ui/Stack';
import { getRouteRools } from '@/shared/const/router';

interface MainMenuProps {
  className?: string;
}

export const MainMenu = memo((props: MainMenuProps) => {
  const { className } = props;
  return (
    <div className={classNames(cls.Menu, {}, [className])}>
      <AppLink variant='btn' to={'game'} className={cls.mainLink}>Играть</AppLink>

      <HStack gap='8' max>
        <VStack gap='8' max>
          <AppLink variant='btn' to={'game'}>Аккаунт</AppLink>
          <AppLink variant='btn' to={'game'}>История игр</AppLink>
        </VStack>
        <VStack gap='8' max>
          <AppLink variant='btn' to={getRouteRools()}>Правила</AppLink>
          <AppLink variant='btn' to={'game'}>Настроки</AppLink>
        </VStack>
      </HStack>
    </div>
  );
});

export default MainMenu;
