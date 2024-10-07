import { configureStore, Reducer, ReducersMapObject } from '@reduxjs/toolkit';
import { rtkApi } from '@/shared/api/rtkApi';
import { StateSchema } from './StateSchema';
import { createReducerManager } from './reducerManager';
import { authReducer } from '@/features/Auth/model/slice/authSlice';
import { authApi } from '@/features/Auth/model/services/authService';
import { chatReducer } from '@/features/Chat/model/slice/chatSlice';
import { chatApi } from '@/features/Chat/model/services/chatService';

export function createReduxStore(
  initialState?: StateSchema,
  asyncReducers?: ReducersMapObject<StateSchema>,
) {
  const rootReducers: ReducersMapObject<StateSchema> = {
    ...asyncReducers,
    auth: authReducer,
    chat: chatReducer,
    [authApi.reducerPath]: authApi.reducer,
    [chatApi.reducerPath]: chatApi.reducer,
  };

  const reducerManager = createReducerManager(rootReducers);

  const middleware = (getDefaultMiddleware: any) =>
    getDefaultMiddleware().concat(rtkApi.middleware);


  const store = configureStore({
    reducer: reducerManager.reduce as Reducer<StateSchema>,
    devTools: __IS_DEV__,
    preloadedState: initialState,
    middleware,
  });

  // @ts-ignore
  store.reducerManager = reducerManager;

  return store;
}

export type AppDispatch = ReturnType<typeof createReduxStore>['dispatch'];
