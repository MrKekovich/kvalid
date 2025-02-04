package io.github.kverify.core.violation

/**
 * Represents a validation violation.
 *
 * Contains a [message] describing the violation.
 */
interface Violation {
    /**
     * Describes the violation.
     */
    val message: String
}
