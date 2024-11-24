import { memo } from 'react';
import { classNames } from '@/shared/lib/classNames/classNames';
import cardBlood from '@/shared/assets/images/card_blood.png';
import cardBloodBack from '@/shared/assets/images/card_blood_back.png';
import cardSand from '@/shared/assets/images/card_sand.png';
import cardSandBack from '@/shared/assets/images/card_sand_back.png';
import cardSandImposter from '@/shared/assets/images/card_sand_imposter.png';
import cardSandSylop from '@/shared/assets/images/card_sand_sylop.png';
import cardBloodImposter from '@/shared/assets/images/card_blood_imposter.png';
import cardBloodSylop from '@/shared/assets/images/card_blood_sylop.png';
import { GameCardType } from '../types/GameCardType';
import cls from './GameCard.module.scss'
import { Card } from '@/features/Game/model/types/game';

export interface GameCardProps {
  type: GameCardType;
  card?: Card;
  onClick?: () => void;
  isFlipped?: boolean;
  isMultiple?: boolean;
}

export const GameCard = memo((props: GameCardProps) => {
  const {
    type,
    card,
    onClick,
    isFlipped = false,
    isMultiple = false,
  } = props;

  // Выбираем изображение карты в зависимости от типа и переворота
  let cardImage = isFlipped
    ? (type === GameCardType.BLOOD ? cardBloodBack : cardSandBack)
    : (type === GameCardType.BLOOD ? cardBlood : cardSand);

  if (isFlipped) {
    return (
      <div className={cls.cardContainer} onClick={onClick}>
        <img src={cardImage} className={classNames(cls.card, {}, [cls.cardBack])} alt="Card back" />
        {isMultiple && <img src={cardImage} className={classNames(cls.card, {}, [cls.cardBack])} alt="Card back" />}
        {isMultiple && <img src={cardImage} className={classNames(cls.card, {}, [cls.cardBack])} alt="Card back" />}
      </div>
    )
  }

  console.log(card, type)
  if (card?.cardValueType === 'IMPOSTER') {
    cardImage = type === GameCardType.BLOOD ? cardBloodImposter : cardSandImposter
  } else if (card?.cardValueType === 'SYLOP') {
    cardImage = type === GameCardType.BLOOD ? cardBloodSylop : cardSandSylop
  }

  return (
    <div className={classNames(cls.card)} onClick={onClick}>
      <img src={cardImage} alt={'Game card'} />
      <span className={classNames(cls.value, {}, [cls[type]])}>{card?.value}</span>
      <span className={classNames(cls.value, {}, [cls.invert, cls[type]])}>{card?.value}</span>
    </div>
  );
});

export default GameCard;
