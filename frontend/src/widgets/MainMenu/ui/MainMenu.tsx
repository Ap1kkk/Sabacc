import { memo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PreAuth from '@/features/Auth/ui/PreAuth';
import { getRouteGame, getRouteLogin, getRouteRools } from '@/shared/const/router';
import { useSessionCheck } from '@/shared/lib/hooks/useSessionCheck';
import { classNames } from '@/shared/lib/classNames/classNames';
import { HStack, Button, Modal, AppLink } from '@/shared/ui';
import cls from './MainMenu.module.scss';

interface MainMenuProps {
  className?: string;
}

export const MainMenu = memo((props: MainMenuProps) => {
  const { className } = props;
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();
  const isValidSession = useSessionCheck();

  const handleOpen = () => {
    if (isValidSession) {
      navigate(getRouteGame());
    } else {
      setIsOpen(true);
    }
  };

  const handleClose = () => setIsOpen(false);

  return (
    <div className={classNames(cls.Menu, {}, [className])}>
      <Button variant="btn" className={cls.mainLink} onClick={handleOpen}>
        Играть
      </Button>

      <Modal isOpen={isOpen} onClose={handleClose}>
        <PreAuth />
      </Modal>

      <HStack gap="8" max>
        <AppLink variant="btn" to={getRouteLogin()}>
          Аккаунт
        </AppLink>
        <AppLink variant="btn" to={getRouteRools()}>
          Правила
        </AppLink>
      </HStack>
    </div>
  );
});

export default MainMenu;
