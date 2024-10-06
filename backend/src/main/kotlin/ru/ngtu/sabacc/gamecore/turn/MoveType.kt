package ru.ngtu.sabacc.gamecore.turn

enum class MoveType {
    PASS,
    GET_SAND,
    GET_BLOOD,
    GET_SAND_DISCARD,
    GET_BLOOD_DISCARD,
    DISCARD_FIRST_SAND,
    DISCARD_FIRST_BLOOD,
    DISCARD_LAST_SAND,
    DISCARD_LAST_BLOOD,
    PLAY_TOKEN
}
