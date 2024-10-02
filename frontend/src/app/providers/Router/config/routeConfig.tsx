import { RouteProps } from 'react-router-dom';
import {
  AppRoutes, getRouteMain, getRouteRools
} from '@/shared/const/router';
import { MainPage } from '@/pages/MainPage';
import { RoolsPage } from '@/pages/RoolsPage';

export const routeConfig: Record<AppRoutes, RouteProps> = {
  [AppRoutes.MAIN]: {
    path: getRouteMain(),
    element: <MainPage />,
  },
  [AppRoutes.ROOLS]: {
    path: getRouteRools(),
    element: <RoolsPage />,
  },
};
