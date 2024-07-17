package io.github.mrkekovich.kvalid.core.exception

/**
 * Represents a validation exception.
 *
 * @property message failure message
 */
class ValidationException(override val message: String) : Throwable()
