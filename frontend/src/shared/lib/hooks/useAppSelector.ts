import { TypedUseSelectorHook, useSelector } from 'react-redux';
import type { AppState } from '@/app/providers/Store';

export const useAppSelector: TypedUseSelectorHook<AppState> = useSelector;
