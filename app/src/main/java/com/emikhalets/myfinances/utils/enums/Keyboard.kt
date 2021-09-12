package com.emikhalets.myfinances.utils.enums

enum class Keyboard(val value: String) {
    NUM_0("0"),
    NUM_1("1"),
    NUM_2("2"),
    NUM_3("3"),
    NUM_4("4"),
    NUM_5("5"),
    NUM_6("6"),
    NUM_7("7"),
    NUM_8("8"),
    NUM_9("9"),
    DOT("."),
    DEL("del");

    companion object {
        private val map = values().associateBy(Keyboard::value)
        fun get(value: String): Keyboard = map[value] ?: NUM_0
    }
}