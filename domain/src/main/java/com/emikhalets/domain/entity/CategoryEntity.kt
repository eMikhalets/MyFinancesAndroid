package com.emikhalets.domain.entity

data class CategoryEntity(
    val id: Long,
    val name: String,
    val type: TransactionType,
)