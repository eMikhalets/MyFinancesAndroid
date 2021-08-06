package com.emikhalets.myfinances.data

import com.emikhalets.myfinances.data.dao.CategoryDao
import com.emikhalets.myfinances.data.dao.TransactionDao
import com.emikhalets.myfinances.data.dao.WalletDao
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val walletDao: WalletDao
) {
}