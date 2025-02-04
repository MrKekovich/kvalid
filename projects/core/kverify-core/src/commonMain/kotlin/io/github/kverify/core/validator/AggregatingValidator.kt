package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation

class AggregatingValidator(
    val violationsStorage: MutableCollection<Violation> = mutableListOf(),
) : ValidationContext {
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }
}

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

fun <T> T.validateAll(
    vararg rules: Rule<T>,
    violationsStorage: MutableCollection<Violation> = mutableListOf(),
): ValidationResult =
    validateAll(violationsStorage) lambda@{
        this@validateAll.applyRules(*rules)
    }

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
