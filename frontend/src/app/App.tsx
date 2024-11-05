import { useTheme } from '@/shared/lib/hooks/useTheme';
import './styles/index.scss'
import { classNames } from '@/shared/lib/classNames/classNames'
import { Suspense, useEffect } from 'react';
import AppRouter from './providers/Router/ui/AppRouter';
import { setUser } from '@/features/Auth/model/slice/authSlice';
import { useAppDispatch } from '@/shared/lib/hooks/useAppDispatch';
import { User } from '@/features/Auth/model/types/auth';
import { USER_LOCALSTORAGE_KEY } from '@/shared/const/localstorage';
import MainImg from '@/shared/assets/images/main.jpg';

const App = () => {
  const { theme } = useTheme();
  const dispatch = useAppDispatch();

  useEffect(() => {
    const userData = localStorage.getItem(USER_LOCALSTORAGE_KEY);
    if (userData && userData !== 'undefined') {
      const user: User = JSON.parse(userData);
      dispatch(setUser(user))
    }
  }, [])

  return (
    <div id="app" className={classNames('app', {}, [theme])}>
      <img className='mainBackgroundImage' src={MainImg} />

      <Suspense fallback={'Loading ...'}>
        <AppRouter />
      </Suspense>
    </div>
  )
}

export default App
