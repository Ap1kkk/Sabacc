import { useContext } from 'react';
import { ThemeContext } from '../context/ThemeContext';
import { Theme } from '../../const/theme';
import { LOCAL_STORAGE_THEME_KEY } from '@/shared/const/localstorage';

interface UseThemeResult {
  toggleTheme: (saveAction?: (theme: Theme) => void) => void;
  theme: Theme;
}

export function useTheme(): UseThemeResult {
  const { theme, setTheme } = useContext(ThemeContext);

  const toggleTheme = () => {
    const newTheme = theme === Theme.LIGHT ? Theme.DARK : Theme.LIGHT;
    setTheme?.(newTheme);
    localStorage.setItem(LOCAL_STORAGE_THEME_KEY, newTheme);
  };

  return {
    theme: theme || Theme.DARK,
    toggleTheme,
  };
}
