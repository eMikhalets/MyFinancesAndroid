package com.emikhalets.myfinances.utils.enums

import com.emikhalets.myfinances.R

enum class MyIcons(val id: Int, val icon: Int) {
    Plus(0, R.drawable.ic_plus),
    Pencil(1, R.drawable.ic_pencil),
    Wallet(2, R.drawable.ic_wallet),
    Money(3, R.drawable.ic_money),
    Minus(4, R.drawable.ic_minus),
    ArrowDown(5, R.drawable.ic_arrow_down),
    Home(6, R.drawable.ic_home),
    PieChart(7, R.drawable.ic_pie_chart),
    PriceList(8, R.drawable.ic_price_list),
    DotsHollow(9, R.drawable.ic_dots_hollow),
    Cutlery(10, R.drawable.ic_cutlery),
    Gift(11, R.drawable.ic_gift),
    Gym(12, R.drawable.ic_gym),
    Healthcare(13, R.drawable.ic_healthcare),
    Loan(14, R.drawable.ic_loan),
    Mortarboard(15, R.drawable.ic_mortarboard),
    Tent(16, R.drawable.ic_tent),
    Train(17, R.drawable.ic_train),
    Tshirt(18, R.drawable.ic_tshirt),
    User(19, R.drawable.ic_user),
    Wifi(20, R.drawable.ic_wifi),
    Salary(21, R.drawable.ic_salary),
    SaveMoney(22, R.drawable.ic_save_money),
    ArrowBack(23, R.drawable.ic_arrow_back),
    App(24, R.drawable.ic_launcher_foreground),
    Calendar(25, R.drawable.ic_calendar);

    companion object {
        private val map = values().associateBy(MyIcons::id)
        fun get(id: Int): MyIcons = map[id] ?: Money
    }
}