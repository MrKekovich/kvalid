package io.github.kverify.dsl.exception

class PropertyAccessException(
    override val message: String,
    override val cause: Throwable? = null,
) : Throwable()
