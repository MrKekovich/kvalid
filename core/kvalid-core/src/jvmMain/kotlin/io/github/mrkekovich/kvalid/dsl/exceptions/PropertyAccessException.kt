package io.github.mrkekovich.kvalid.dsl.exceptions

/**
 * Exception thrown when there is an error accessing a property.
 *
 * This exception is used to indicate that a property value could not be accessed,
 * typically because the property was accessed without an instance.
 *
 * @param message A detailed message explaining the reason for the exception.
 */
class PropertyAccessException(override val message: String) : Throwable()
