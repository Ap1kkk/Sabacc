import { RouteProps } from 'react-router-dom';
import {
  AppRoutes, getRouteMain,
} from '@/shared/const/router';
import { MainPage } from '@/pages/MainPage';

export const routeConfig: Record<AppRoutes, RouteProps> = {
  [AppRoutes.MAIN]: {
    path: getRouteMain(),
    element: <MainPage />,
  },
};
