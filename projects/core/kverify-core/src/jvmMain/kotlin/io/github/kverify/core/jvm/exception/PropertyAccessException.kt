package io.github.kverify.core.jvm.exception

class PropertyAccessException(
    override val message: String,
    override val cause: Throwable? = null,
) : Throwable()
