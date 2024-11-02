import { memo } from 'react';
import { AppIcon } from '@/shared/ui';
import LightThemeIcon from '@/shared/assets/icons/sun.svg';
import DarkThemeIcon from '@/shared/assets/icons/moon.svg';
import { useTheme } from '@/shared/lib/hooks/useTheme';
import { Theme } from '@/shared/const/theme';

interface ThemeSwitcherProps {
  className?: string;
}

export const ThemeSwitcher = memo(({ className }: ThemeSwitcherProps) => {
  const { theme, toggleTheme } = useTheme();

  const onToggleHandler = () => {
    toggleTheme();
  };

  const Svg = theme == Theme.LIGHT ? LightThemeIcon : DarkThemeIcon

  return (
    <AppIcon
      className={className}
      Svg={Svg}
      clickable
      onClick={onToggleHandler}
    />
  );
});
