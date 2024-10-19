package io.github.kverify.core.exception

/**
 * Represents a validation exception.
 *
 * @property message failure message
 */
class ValidationException(
    override val message: String,
    override val cause: Throwable? = null,
) : Throwable()
