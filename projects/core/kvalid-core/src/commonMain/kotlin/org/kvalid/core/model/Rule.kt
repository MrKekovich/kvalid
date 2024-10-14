package org.kvalid.core.model

import org.kvalid.core.context.Predicate

/**
 * Represents a validation rule with a failure message and a validation function.
 */
interface Rule {
    /**
     * The message to be used if the validation fails.
     */
    val failMessage: String

    /**
     * Executes the validation rule.
     *
     * @return true if the validation passes, false otherwise.
     */
    fun validate(): Boolean

    companion object {
        /**
         * Creates a new Rule instance with the given message and predicate.
         *
         * @param message The failure message for the rule.
         * @param predicate The validation function.
         * @return A new Rule instance.
         */
        operator fun invoke(
            message: String,
            predicate: Predicate,
        ): Rule =
            object : Rule {
                override val failMessage: String = message

                override fun validate(): Boolean = predicate()
            }

        /**
         * Creates a new Rule instance with the given message and condition.
         *
         * @param message The failure message for the rule.
         * @param condition This value will be returned on [Rule.validate].
         * @return A new [Rule] instance with specified [message] and [condition].
         */
        operator fun invoke(
            message: String,
            condition: Boolean,
        ): Rule =
            object : Rule {
                override val failMessage: String = message

                override fun validate(): Boolean = condition
            }

        /**
         * Creates a new [Rule] instance with the given failure message, that always fails.
         *
         * @param message the failure message
         * @return A new [Rule] instance that always fails.
         */
        fun failure(message: String): Rule =
            object : Rule {
                override val failMessage: String = message

                override fun validate(): Boolean = false
            }
    }
}
