export enum AppRoutes {
  MAIN = '',
  ROOLS = 'rools',
  GAME = 'game',
  PROFILE = 'profile',
  LOGIN = 'login'
}

export const getRouteMain = () => `/`;
export const getRouteRools = () => `/rools`;
export const getRouteGame = () => `/game`;
export const getRouteProfile = () => `/profile`;
export const getRouteLogin = () => `/login`;

export const AppRouteByPathPattern: Record<string, AppRoutes> = {
  [getRouteMain()]: AppRoutes.MAIN,
  [getRouteRools()]: AppRoutes.ROOLS,
  [getRouteGame()]: AppRoutes.GAME,
  [getRouteProfile()]: AppRoutes.PROFILE,
  [getRouteLogin()]: AppRoutes.LOGIN
};
