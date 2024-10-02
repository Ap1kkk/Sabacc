import { memo } from 'react';
import cls from './MainMenu.module.scss'
import { AppLink } from '@/shared/ui/AppLink';
import { classNames } from '@/shared/lib/classNames/classNames';
import { HStack } from '@/shared/ui/Stack';
import HistoryIcon from '@/shared/assets/icons/history.png'
import AccauntIcon from '@/shared/assets/icons/accaunt.png'
import ParamsIcon from '@/shared/assets/icons/params.png'
import QuestionsIcon from '@/shared/assets/icons/questions.png'
import { AppIcon } from '@/shared/ui/AppIcon';

interface MainMenuProps {
  className?: string;
}

export const MainMenu = memo((props: MainMenuProps) => {
  const { className } = props;
  return (
    <div className={classNames(cls.Menu, {}, [className])}>
      <AppLink variant='btn' to={'game'} className={cls.mainLink}>Играть</AppLink>
      
      <HStack gap='8' max>
        <AppIcon Svg={AccauntIcon} />
      {/*   <AppLink variant='btn' to={'game'} icon={AccauntIcon}>Аккаунт</AppLink>
        <AppLink variant='btn' to={'game'}>Настройки</AppLink> */}
      </HStack>

      <HStack gap='8' max>
        <AppLink variant='btn' to={'game'}>История игр</AppLink>
        <AppLink variant='btn' to={'game'}>Правила</AppLink>
      </HStack>
    </div>
  );
});

export default MainMenu;
