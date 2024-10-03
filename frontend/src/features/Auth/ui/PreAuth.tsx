import { HStack } from '@/shared/ui/Stack';
import { ChangeEvent, memo, useRef, useState } from 'react';
import cls from './PreAuth.module.scss'
import { AppLink } from '@/shared/ui/AppLink';
import { Button } from '@/shared/ui/Button';
import { useCreateAnonymousUserMutation } from '../model/services/authService';


export const PreAuth = memo(() => {
  const [isValidName, setIsValidName] = useState<boolean>(false);
  const [isStartingType, setIsStartingType] = useState<boolean>(false);
  const [createAnonymousUser] = useCreateAnonymousUserMutation();
  const inputNameRef = useRef<HTMLInputElement | null>(null)

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setIsStartingType(true);

    if (e.target.value.length >= 4) {
      setIsValidName(true)
    } else {
      setIsValidName(false)
    }
  }

  const handleToGame = () => {
    if (inputNameRef.current?.value) {
      createAnonymousUser({ username: inputNameRef.current?.value })
    }
  }

  return (
    <div className={cls.modal}>
      <HStack className={cls.inputWrapper} max>
        <input
          className={cls.input}
          placeholder='Введите имя'
          onChange={handleInputChange}
          ref={inputNameRef}
        />

        {
          isValidName &&
          <Button className={cls.goToGame} onClick={handleToGame}>В игру</Button>
        }

        {
          (!isValidName && isStartingType) &&
          <span className={cls.error}>Минимум 4 символа.</span>
        }
      </HStack>

      <p>или</p>

      <AppLink className={cls.registerBtn} variant='dark' to={'auth'}>Зарегистрируйтесь!</AppLink>
    </div>
  );
});

export default PreAuth;
