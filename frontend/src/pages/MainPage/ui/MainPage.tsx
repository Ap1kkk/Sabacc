
import { memo } from 'react';
import MainMenu from '@/widgets/MainMenu/ui/MainMenu';
import MainImg from '@/shared/assets/images/main.jpg'
import { BackgroundImg } from '@/shared/ui/BackgroundImg';

export const MainPage = memo(() => {
  return (
    <div>
      <BackgroundImg src={MainImg} />
      <MainMenu />
    </div>
  );
});

export default MainPage;
