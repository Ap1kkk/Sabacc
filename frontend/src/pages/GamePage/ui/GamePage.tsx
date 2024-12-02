import { classNames } from '@/shared/lib/classNames/classNames';
import cls from './GamePage.module.scss';
import { Game } from '@/features/Game';
import { useGameState } from '@/features/Game/model/hooks/useGameState';

const GamePage = () => {
  const {
    client,
    gameState,
    roomState,
    isLoading,
    isGameInProgress,
    diceDetails,
    handleDiceSelection,
    winnerId,
    roundResult,
    leaveCurrentRoom
  } = useGameState();

  if (!client) return <div>Проблема с вебсокетом</div>;
  if (!roomState) return <div>Отсутствие состояния комнаты</div>;
  if (!gameState) return <div>Отсутствие состояния игры</div>;

  return (
    <div className={classNames(cls.game, {}, [])}>
      {isLoading || !isGameInProgress ? (
        <div className={classNames(cls.loader, {}, [])}>Ожидание соперника...</div>
      ) : (
        <Game
          client={client}
          gameState={gameState}
          roomState={roomState}
          diceDetails={diceDetails}
          handleDiceSelection={handleDiceSelection}
          winnerId={winnerId!} // Передаем ID победителя
          roundResult={roundResult} // Передаем результаты раунда
          leaveCurrentRoom={leaveCurrentRoom}
        />
      )}
    </div>
  );
};

export default GamePage;
