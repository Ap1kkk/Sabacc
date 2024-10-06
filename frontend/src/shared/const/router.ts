export enum AppRoutes {
  MAIN = '',
  ROOLS = 'rools',
  GAME = 'game'
}

export const getRouteMain = () => `/`;
export const getRouteRools = () => `/rools`;
export const getRouteGame = () => `/game`;
// export const getRouteCatalog = () => `/catalog`;
// export const getRouteSignUp = () => `/signup`;
// export const getRouteSignIn = () => `/signin`;
// export const getRouteProfile = () => `/profile`;
// export const getRouteOwner = (id: string) => `/owner/${id}`;
// export const getRoutePlacementCreate = () => `/placement/create`;
// export const getRoutePlacementEdit = (id: string) => `/placement/${id}/edit`;
// export const getRoutePlacementDetail = (id: string) => `/placement/${id}`;
// export const getRoutePlacementBooking = (id: string) => `/placement/${id}/booking`;
// export const getRoutePlacementReviews = (id: string) => `/placement/${id}/reviews`;
// export const getRoutePlacementChat = (id: string) => `/placement/${id}/chat`;
// export const getRouteAdminPanel = () => `/admin-panel`;
// export const getRouteForbidden = () => '/forbidden';

export const AppRouteByPathPattern: Record<string, AppRoutes> = {
  [getRouteMain()]: AppRoutes.MAIN,
  [getRouteRools()]: AppRoutes.ROOLS,
  [getRouteGame()]: AppRoutes.GAME,
  // [getRouteCatalog()]: AppRoutes.CATALOG,
  // [getRouteSignUp()]: AppRoutes.SIGN_UP,
  // [getRouteSignIn()]: AppRoutes.SIGN_IN,
  // [getRouteProfile()]: AppRoutes.PROFILE,
  // [getRouteOwner(':id')]: AppRoutes.OWNER,
  // [getRoutePlacementCreate()]: AppRoutes.PLACEMENT_CREARE,
  // [getRoutePlacementEdit(':id')]: AppRoutes.PLACEMENT_EDIT,
  // [getRoutePlacementDetail(':id')]: AppRoutes.PLACEMENT_DETAIL,
  // [getRoutePlacementBooking(':id')]: AppRoutes.PLACEMENT_BOOKING,
  // [getRoutePlacementReviews(':id')]: AppRoutes.PLACEMENT_REVIEWS,
  // [getRoutePlacementChat(':id')]: AppRoutes.PLACEMENT_CHAT,
  // [getRouteAdminPanel()]: AppRoutes.ADMIN_PANEL,
  // [getRouteForbidden()]: AppRoutes.FORBIDDEN,
};
