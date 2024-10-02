import { ReactNode, useEffect, useMemo, useState } from 'react';
import { Theme } from '@/shared/const/theme';
import { LOCAL_STORAGE_THEME_KEY } from '@/shared/const/localstorage';
import { ThemeContext } from '@/shared/lib/context/ThemeContext';

interface ThemeProviderProps {
  children: ReactNode;
}

const selectedTheme = localStorage.getItem(LOCAL_STORAGE_THEME_KEY) as Theme;

const ThemeProvider = (props: ThemeProviderProps) => {
  const { children } = props;

  const [theme, setTheme] = useState<Theme>(selectedTheme || Theme.DARK);

  useEffect(() => {
    document.body.className = theme;
    localStorage.setItem(LOCAL_STORAGE_THEME_KEY, theme);
  }, [theme]);

  const selectedProps = useMemo(() => ({ theme, setTheme }), [theme]);

  return <ThemeContext.Provider value={selectedProps}>{children}</ThemeContext.Provider>;
};

export default ThemeProvider;
