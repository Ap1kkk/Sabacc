import { memo, useState } from 'react';
import cls from './MainMenu.module.scss'
import { AppLink } from '@/shared/ui/AppLink';
import { classNames } from '@/shared/lib/classNames/classNames';
import { HStack, VStack } from '@/shared/ui/Stack';
import { getRouteRools } from '@/shared/const/router';
import { Button } from '@/shared/ui/Button';
import { Modal } from '@/shared/ui/Modal';
import PreAuth from '@/features/Auth/ui/PreAuth';

interface MainMenuProps {
  className?: string;
}

export const MainMenu = memo((props: MainMenuProps) => {
  const { className } = props;
  const [isOpen, setIsOpen] = useState(false);

  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);

  return (
    <div className={classNames(cls.Menu, {}, [className])}>
      <Button variant='btn' className={cls.mainLink} onClick={handleOpen}>Играть</Button>

      <Modal isOpen={isOpen} onClose={handleClose}>
        <PreAuth />
      </Modal>

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
