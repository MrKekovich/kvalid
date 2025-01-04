package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.validator.AggregatingValidator

/**
 * Collects all validation violations into a single [ValidationException].
 *
 * Executes the provided [block] of validation logic using an [AggregatingValidator].
 * If any validation failures occur, a [ValidationException] is returned,
 * containing all violation messages. If no failures occur, `null` is returned.
 *
 * @param block The validation logic to execute
 * @return A [ValidationException] if there are validation failures, or `null` if all validations pass
 */
inline fun validateAll(block: AggregatingValidator.() -> Unit): ValidationException? {
    val violationMessages = AggregatingValidator().apply(block).violationMessages

    return if (violationMessages.isNotEmpty()) {
        ValidationException(violationMessages = violationMessages)
    } else {
        null
    }
}

/**
 * Validates the receiver against the provided [rules],
 * aggregating any validation violations into a single [ValidationException].
 *
 * Uses [validateAll] to perform the validation. If any rules fail,
 * the resulting [ValidationException] contains all violation messages.
 *
 * @param T The type of the value being validated
 * @param rules The rules to validate the receiver against
 * @return A [ValidationException] if there are validation failures, or `null` if all validations pass
 */
fun <T> T.validateAll(vararg rules: Rule<T>): ValidationException? =
    validateAll lambda@{
        this@validateAll.validate(*rules)
    }
