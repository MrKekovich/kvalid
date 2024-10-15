package io.github.kverify.dsl.exception

class PropertyAccessException(
    override val message: String,
    cause: Throwable? = null,
) : Throwable(cause = cause)
