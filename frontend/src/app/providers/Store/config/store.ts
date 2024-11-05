// src/app/providers/Store/config/store.ts

import { configureStore, Reducer, ReducersMapObject, AnyAction } from '@reduxjs/toolkit';
import { rtkApi } from '@/shared/api/rtkApi';
import { StateSchema } from './StateSchema';
import { createReducerManager } from './reducerManager';
import { authReducer } from '@/features/Auth/model/slice/authSlice';
import { chatReducer } from '@/features/Chat/model/slice/chatSlice';

// Определим тип для asyncReducers, исключив из него ключи 'auth' и 'chat'
type AsyncReducers = Omit<ReducersMapObject<StateSchema>, 'auth' | 'chat'>;

export function createReduxStore(
  initialState?: StateSchema,
  asyncReducers?: AsyncReducers,
) {
  // Статические редьюсеры
  const staticReducers: ReducersMapObject<StateSchema> = {
    auth: authReducer,
    chat: chatReducer,
    [rtkApi.reducerPath]: rtkApi.reducer, // Добавляем только один раз
  };

  // Комбинируем статические редьюсеры с асинхронными
  const rootReducers: ReducersMapObject<StateSchema> = {
    ...staticReducers,
    ...(asyncReducers || {}),
  };

  const reducerManager = createReducerManager(rootReducers);

  const middleware = (getDefaultMiddleware: any) =>
    getDefaultMiddleware().concat(
      rtkApi.middleware, // Добавляем только один раз
    );

  const store = configureStore({
    reducer: reducerManager.reduce as Reducer<StateSchema, AnyAction>,
    devTools: __IS_DEV__,
    preloadedState: initialState,
    middleware,
  });

  // @ts-ignore
  store.reducerManager = reducerManager;

  return store;
}

export type AppDispatch = ReturnType<typeof createReduxStore>['dispatch'];
