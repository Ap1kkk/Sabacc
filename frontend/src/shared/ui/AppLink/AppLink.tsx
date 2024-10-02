import { LinkProps, NavLink } from 'react-router-dom';
import { memo, ReactNode } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import cls from './AppLink.module.scss';
import { AppIcon } from '../AppIcon';

export type AppLinkVariant = 'default' | 'main' | 'btn';

interface AppLinkProps extends LinkProps {
  className?: string;
  variant?: AppLinkVariant;
  icon?: React.FC<React.SVGProps<SVGSVGElement>>;
  children?: ReactNode;
  activeClassName?: string;
}

export const AppLink = memo((props: AppLinkProps) => {
  const {
    to,
    className,
    children,
    variant = 'default',
    icon,
    activeClassName = '',
    ...otherProps
  } = props;


  return (
    <NavLink
      to={to}
      className={({ isActive }) =>
        classNames(cls.AppLink, { [activeClassName]: isActive, [cls.withIcon]: !!icon}, [
          className,
          cls[variant],
        ])
      }
      {...otherProps}
    >
      {icon && <AppIcon Svg={icon} className={cls.linkIcon}></AppIcon>}
      {children}
    </NavLink>
  );
});
