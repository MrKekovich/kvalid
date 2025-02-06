package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation

/**
 * Implementation of the [ValidationContext], that
 * collects [Violation]s reported via [ValidationContext.onFailure]
 * and stores them in [violationsStorage].
 */
class AggregatingValidator(
    val violationsStorage: MutableCollection<Violation> = mutableListOf(),
) : ValidationContext {
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }
}

/**
 * Executes the given [block] within an [AggregatingValidator] context,
 * collecting [Violation]s reported via [ValidationContext.onFailure]
 * and storing them in [violationsStorage].
 *
 * Note: A copy of [violationsStorage] will be used in the [ValidationResult].
 *
 * @return [ValidationResult] containing all [Violation]s from [violationsStorage].
 */
inline fun validateAll(
    violationsStorage: MutableCollection<Violation> = mutableListOf(),
    block: AggregatingValidator.() -> Unit,
): ValidationResult =
    ValidationResult(
        AggregatingValidator(violationsStorage)
            .apply(block)
            .violationsStorage
            .toList(),
    )

/**
 * Applies all [rules] to this value within an [AggregatingValidator] context,
 * collecting [Violation]s reported via [ValidationContext.onFailure]
 * and storing them in [violationsStorage].
 *
 * Note: This function will create a copy of [violationsStorage] to use in [ValidationResult]
 *
 * @return [ValidationResult] containing all [Violation]s from [violationsStorage].
 */
fun <T> T.validateAll(
    vararg rules: Rule<T>,
    violationsStorage: MutableCollection<Violation> = mutableListOf(),
): ValidationResult =
    validateAll(violationsStorage) lambda@{
        this@validateAll.applyRules(*rules)
    }

/**
 * Runs the given [block] within an [AggregatingValidator] context,
 * collecting [Violation]s reported via [ValidationContext.onFailure]
 * and storing them in [violationsStorage].
 *
 * Note: This function will create a copy of [violationsStorage] to use in [ValidationResult]
 *
 * @return [Result.success] if [violationsStorage] is empty,
 * otherwise returns [Result.failure] wrapping a [ValidationException]
 * with all collected [Violation]s
 */
inline fun <T> runValidatingAll(
    violationsStorage: MutableCollection<Violation> = mutableListOf(),
    block: AggregatingValidator.() -> T,
): Result<T> {
    val aggregatingValidator = AggregatingValidator(violationsStorage)
    val result = aggregatingValidator.run(block)

    val violations = aggregatingValidator.violationsStorage.toList()

    return if (violations.isEmpty()) {
        Result.success(result)
    } else {
        Result.failure(
            ValidationException(violations),
        )
    }
}
