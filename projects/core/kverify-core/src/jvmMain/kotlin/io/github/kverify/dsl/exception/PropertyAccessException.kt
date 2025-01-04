package io.github.kverify.dsl.exception

/**
 * Exception thrown when accessing a property fails due to reflection-related issues.
 *
 * This exception is thrown when there is a problem accessing a property using reflection,
 * such as illegal access or inability to retrieve the property value.
 *
 * @property message The detailed message explaining the reason for the exception.
 * @property cause The underlying exception that caused this exception, if any.
 */
class PropertyAccessException(
    override val message: String,
    override val cause: Throwable? = null,
) : Throwable()
