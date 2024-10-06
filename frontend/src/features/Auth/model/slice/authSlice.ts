import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { AuthSchema } from '../types/authSchema';
import { authApi } from '../services/authService';
import { User } from '../types/auth';

const initialState: AuthSchema = {
  user: null,
  error: null
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout: (state) => {
      state.user = null;
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addMatcher(authApi.endpoints.createAnonymousUser.matchFulfilled, (state, action: PayloadAction<{ user: User }>) => {
        state.user = action.payload.user;
      })
      .addMatcher(authApi.endpoints.createAnonymousUser.matchRejected, (state, action: PayloadAction<any>) => {
        state.error = action.payload;
      })
  },
});

export const { logout } = authSlice.actions;

export const authReducer  = authSlice.reducer;
