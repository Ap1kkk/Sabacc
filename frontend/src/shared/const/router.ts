export enum AppRoutes {
  MAIN = '',
  ROOLS = 'rools',
  GAME = 'game'
}

export const getRouteMain = () => `/`;
export const getRouteRools = () => `/rools`;
export const getRouteGame = () => `/game`;

export const AppRouteByPathPattern: Record<string, AppRoutes> = {
  [getRouteMain()]: AppRoutes.MAIN,
  [getRouteRools()]: AppRoutes.ROOLS,
  [getRouteGame()]: AppRoutes.GAME
};
