package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.validator.LazyValidator

/**
 * Represents a sequence of [ValidationException]s.
 */
typealias ViolationSequence = Sequence<ValidationException>

/**
 * Converts a [LazyValidator] to a [Sequence] of [ValidationException] instances.
 *
 * The [ViolationSequence] is generated lazily, yielding elements only when accessed.
 *
 * @return A [Sequence] of [ValidationException] representing the outcome of the validation.
 */
fun LazyValidator.asViolationSequence(): Sequence<ValidationException> =
    sequence {
        for ((failureMessage, predicate) in rules) {
            if (!predicate()) yield(ValidationException(failureMessage))
        }
    }

/**
 * Lazily validates conditions defined within the given block using a [LazyValidator].
 * Returns a lazy sequence of [ValidationException] that occurs during validation.
 * The validation is evaluated only upon accessing the elements of the sequence.
 * This allows deferred evaluation and efficient handling of validation rules.
 *
 * **Note**: The sequence remains unevaluated until elements are accessed. For example:
 * ```
 * var count = 0
 * val result = validateLazy {
 *     rule("something") {
 *         count++
 *         true
 *     }
 * }
 * println(count) // 0
 * result.firstOrNull() // Forces evaluation; count becomes 1
 * result.toList() // Further evaluation; count becomes 2
 * println(count) // 2
 * ```
 *
 * @param block The block of validation conditions to lazily execute.
 * @return Lazy sequence of [ValidationException] representing violations found during lazy validation.
 *
 * @see LazyValidator
 * @see ValidationException
 * @see Sequence
 */
inline fun validateLazily(block: LazyValidator.() -> Unit): Sequence<ValidationException> =
    LazyValidator()
        .apply(block)
        .asViolationSequence()

fun <T> T.validateLazily(vararg rules: Rule<T>): Sequence<ValidationException> =
    validateLazily {
        validate(*rules)
    }

/**
 * Converts a sequence of [ValidationException] instances to a [ValidationResult].
 *
 * @return A [ValidationResult] representing the outcome of the validation.
 */
fun Sequence<ValidationException>.toValidationResult(): ValidationResult {
    val violations = this.toList()

    return ValidationResult(violations)
}
