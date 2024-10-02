import { memo } from 'react';
import { Route, RouteProps, Routes } from 'react-router-dom';
import { routeConfig } from '../config/routeConfig';

const AppRouter = () => {
  return <Routes>
    {Object.values(routeConfig).map((route: RouteProps) => {
      return <Route
        key={route.path}
        path={route.path}
        element={route.element}
      />
    })}
  </Routes>;
};

export default memo(AppRouter);
