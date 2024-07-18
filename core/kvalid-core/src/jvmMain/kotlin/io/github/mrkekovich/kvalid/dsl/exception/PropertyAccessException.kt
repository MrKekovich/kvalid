package io.github.mrkekovich.kvalid.dsl.exception

class PropertyAccessException(override val message: String, cause: Throwable? = null) : Throwable(cause = cause)
