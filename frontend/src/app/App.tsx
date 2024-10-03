import { useTheme } from '@/shared/lib/hooks/useTheme';
import './styles/index.scss'
import { classNames } from '@/shared/lib/classNames/classNames'
import { Suspense } from 'react';
import AppRouter from './providers/Router/ui/AppRouter';
import { BackgroundImg } from '@/shared/ui/BackgroundImg';

const App = () => {
  const { theme } = useTheme();

  return (
    <div id="app" className={classNames('app', {}, [theme])}>
      <BackgroundImg />

      <Suspense fallback={'Loading ...'}>
        <AppRouter />
      </Suspense>
      


    </div>
  )
}

export default App
