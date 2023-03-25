package com.emikhalets.domain.entity

import com.emikhalets.core.UNDEFINED_ID
import com.emikhalets.core.UiString

sealed class ResultWrapper<out T>(val code: Int) {

    class Success<T>(val data: T?, status: Int = UNDEFINED_ID) : ResultWrapper<T>(status)

    class Error<T>(val message: UiString, status: Int = UNDEFINED_ID) : ResultWrapper<T>(status)
}