import { User } from '../types/auth';
import { rtkApi } from '@/shared/api/rtkApi';

export const authApi = rtkApi.injectEndpoints({
  endpoints: (builder) => ({
    createAnonymousUser: builder.mutation<User, { username: string }>({
      query: (userData) => ({
        url: 'users/login',
        method: 'POST',
        body: userData,
      }),
    }),
  }),
});

export const { useCreateAnonymousUserMutation } = authApi;
