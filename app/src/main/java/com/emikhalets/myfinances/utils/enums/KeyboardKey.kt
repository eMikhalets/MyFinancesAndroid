package com.emikhalets.myfinances.utils.enums

enum class KeyboardKey(val value: String) {
    Num0("0"),
    Num1("1"),
    Num2("2"),
    Num3("3"),
    Num4("4"),
    Num5("5"),
    Num6("6"),
    Num7("7"),
    Num8("8"),
    Num9("9"),
    Dot("."),
    Delete("⌫");

    companion object {
        private val map = values().associateBy(KeyboardKey::value)
        fun get(value: String): KeyboardKey = map[value] ?: Num0

        fun KeyboardKey.isDigit(): Boolean {
            return this == Num0
                    || this == Num1
                    || this == Num2
                    || this == Num3
                    || this == Num4
                    || this == Num5
                    || this == Num6
                    || this == Num7
                    || this == Num8
                    || this == Num9
        }
    }
}