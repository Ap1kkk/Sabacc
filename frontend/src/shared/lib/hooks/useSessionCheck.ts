// shared/lib/hooks/useSessionCheck.ts
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch } from './useAppDispatch';
import { selectCurrentUser } from '@/features/Auth/model/selectors/selectCurrentUser';
import { checkSessionExpiration } from '@/features/Auth/model/slice/authSlice';

export const useSessionCheck = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const user = useSelector(selectCurrentUser);
  const [isValid, setIsValid] = useState<boolean>(true)

  useEffect(() => {
    dispatch(checkSessionExpiration());

    if (!user) {
      setIsValid(false)
    }
  }, [dispatch, user, navigate]);

  return isValid;
};
