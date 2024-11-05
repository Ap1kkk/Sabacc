# Backend

## Запуск

Бэкенд приложения запускается командой `docker-compose up -d backend`

### Порты по умолчанию

Бэкенд : `8080` \
БД: `5432`

## Схема БД

Используется Flyway для миграций. \
Скрипты миграций можно найти [здесь](src/main/resources/db).

## Swagger

Swagger-UI доступен по ссылке [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

## Игровое взаимодействие

### Сокет

Подключение локально: `ws://localhost:8080/game?sessionId=2&playerId=3`

PlayerId = UserId

Эндпоинты для сокета [тут](src/main/java/ru/ngtu/sabacc/constants/WebSocketApiEndpoint.java) \
Перед каждым эндпоинтом добавляется `/app`

Получение текущего состояния игры: `http://localhost:8080/api/v1/room/game/current-state?sessionId=666`

### Примеры DTO

---

**[Ошибка хода](src/main/kotlin/ru/ngtu/sabacc/gamecore/game/GameErrorDto.kt)**
### Еще в разработке!
Эндпоинт: `WS_USER_SESSION_ERRORS_QUEUE` \
Типы ошибок: [enum](src/main/kotlin/ru/ngtu/sabacc/gamecore/game/GameErrorType.kt)
```json
{
  "errorType": "GAME_ON_PAUSE", // NOT_YOUR_TURN, WRONG_MOVE, WRONG_MOVE, TOKEN_NOT_FOUND
  "details": {}
}
```
---
DTO на ход одинаковые для этих эндпоинтов: \
`WS_ACCEPTED_TURNS_QUEUE` -> Слушать ходы подтвержденные игрой\
`WS_SESSION_TURN_INPUT` -> Отправлять ходы игрока
---

**[Спасовать](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnDto.kt)** \
Типы ходов: [enum](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnType.kt)
```json
{
  "sessionId": 666,
  "playerId": 777,
  "turnType": "PASS"
}
```

---

**[Использовать токен](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnDto.kt)** \
Типы ходов: [enum](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnType.kt) \
Типы токенов: [enum](src/main/kotlin/ru/ngtu/sabacc/gamecore/token/Token.kt)
```json
{
  "sessionId": 666,
  "playerId": 777,
  "turnType": "PLAY_TOKEN"
}
```

---

**[Взять карту](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnDto.kt)** \
Типы ходов: [enum](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnType.kt)
```json
{
  "sessionId": 666,
  "playerId": 777,
  "turnType": "GET_SAND" // GET_BLOOD, GET_SAND_DISCARD, GET_BLOOD_DISCARD
}
```

---

**[Сбросить карту](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnDto.kt)** \
Типы ходов: [enum](src/main/kotlin/ru/ngtu/sabacc/gamecore/turn/TurnType.kt)
```json
{
  "sessionId": 666,
  "playerId": 777,
  "turnType": "DISCARD_SAND" // DISCARD_BLOOD
  "details": {
    "index": 0 // 1
  }
}
```

---

Выброс и выбор кубика еще в разработке
