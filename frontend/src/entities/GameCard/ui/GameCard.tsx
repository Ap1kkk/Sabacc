import { memo } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import cardBlood from '@/shared/assets/images/card_blood.png';
import cardBloodBack from '@/shared/assets/images/card_blood_back.png';
import cardSand from '@/shared/assets/images/card_sand.png';
import cardSandBack from '@/shared/assets/images/card_sand_back.png';
import { GameCardType } from '../types/GameCardType';
import cls from './GameCard.module.scss'

export interface GameCardProps {
  type: GameCardType;
  value?: number | 'IMPOSTER';
  isFlipped?: boolean;
  isMultiple?: boolean;
}

export const GameCard = memo((props: GameCardProps) => {
  const {
    type,
    value,
    isFlipped = false,
    isMultiple = false,
  } = props;

  // Выбираем изображение карты в зависимости от типа и переворота
  const cardImage = isFlipped
    ? (type === GameCardType.BLOOD ? cardBloodBack : cardSandBack)
    : (type === GameCardType.BLOOD ? cardBlood : cardSand);

  if (isFlipped) {
    return (
      <div className={cls.cardContainer}>
        <img src={cardImage} className={classNames(cls.card, {}, [cls.cardBack])} alt="Card back" />
        {isMultiple && <img src={cardImage} className={classNames(cls.card, {}, [cls.cardBack])} alt="Card back" />}
        {isMultiple && <img src={cardImage} className={classNames(cls.card, {}, [cls.cardBack])} alt="Card back" />}
      </div>
    )
  }

  return (
    <div className={classNames(cls.card)}>
      <img src={cardImage} alt={'Game card'} />
      <span className={classNames(cls.value, {}, [cls[type]])}>{value}</span>
      <span className={classNames(cls.value, {}, [cls.invert, cls[type]])}>{value}</span>
    </div>
  );
});

export default GameCard;
