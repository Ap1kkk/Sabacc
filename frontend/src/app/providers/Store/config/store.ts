import { productReducer } from '@/entities/Product/model/slice/productSlice';
import { configureStore } from '@reduxjs/toolkit';
import { combineReducers } from 'redux';

const rootReducer = combineReducers({
  products: productReducer
});

export const store = configureStore({
  reducer: rootReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware(),
  devTools: __IS_DEV__, 
});

export type AppState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof store.dispatch;
