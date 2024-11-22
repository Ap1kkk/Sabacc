import { classNames } from '@/shared/lib/classNames/classNames';
import cls from './GamePage.module.scss';
import { Game } from '@/features/Game';
import { useGameState } from '@/features/Game/model/hooks/useGameState';

const GamePage = () => {
  const { client, gameState, roomState, isLoading, isGameInProgress } = useGameState();

  if (!client) return <div>проблема с вебсокетом</div>
  if (!roomState) return <div>отсутствие состояния комнаты</div>
  if (!gameState) return <div>отсутствие состояния игры</div>

  return (
    <div className={classNames(cls.game, {}, [])}>
      {isLoading || !isGameInProgress ? (
        <div className={classNames(cls.loader, {}, [])}>Ожидание соперника...</div>
      ) : (
        <Game client={client} gameState={gameState} roomState={roomState} />
      )}
    </div>
  );
};

export default GamePage;
