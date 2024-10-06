import { RouteProps } from 'react-router-dom';
import {
  AppRoutes, getRouteGame, getRouteMain, getRouteRools
} from '@/shared/const/router';
import { MainPage } from '@/pages/MainPage';
import { RoolsPage } from '@/pages/RoolsPage';
import { GamePage } from '@/pages/GamePage';

export const routeConfig: Record<AppRoutes, RouteProps> = {
  [AppRoutes.MAIN]: {
    path: getRouteMain(),
    element: <MainPage />,
  },
  [AppRoutes.ROOLS]: {
    path: getRouteRools(),
    element: <RoolsPage />,
  },
  [AppRoutes.GAME]: {
    path: getRouteGame(),
    element: <GamePage />,
  },
};
