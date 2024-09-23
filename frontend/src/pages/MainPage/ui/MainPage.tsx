
import { memo } from 'react';
import cls from './MainPage.module.scss'
import MainMenu from '@/widgets/MainMenu/ui/MainMenu';

export const MainPage = memo(() => {
  return (
    <div>
      <img className={cls.img} src='src/shared/assets/images/main.jpg'/>
      <MainMenu />
    </div>
  );
});

export default MainPage;
