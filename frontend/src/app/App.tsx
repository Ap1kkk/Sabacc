import { useTheme } from '@/shared/lib/hooks/useTheme';
import './styles/index.scss'
import { classNames } from '@/shared/lib/classNames/classNames'
import { Suspense } from 'react';
import AppRouter from './providers/Router/ui/AppRouter';

const App = () => {
  const { theme } = useTheme();

  return (
    <div id="app" className={classNames('app', {}, [theme])}>


      <Suspense fallback={'Loading ...'}>
        <AppRouter />
      </Suspense>
      


    </div>
  )
}

export default App
