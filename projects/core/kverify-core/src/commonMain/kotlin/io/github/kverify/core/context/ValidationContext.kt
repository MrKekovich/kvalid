package io.github.kverify.core.context

import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.runValidation
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation

fun interface ValidationContext {
    fun onFailure(violation: Violation)

    fun <T> T.applyRules(vararg rules: Rule<T>): T {
        rules.forEach {
            it.runValidation(
                context = this@ValidationContext,
                value = this@applyRules,
            )
        }
        return this
    }
}

fun ValidationContext.onFailure(message: String): Unit =
    onFailure(
        message.asViolation(),
    )

fun ValidationContext.applyUnitRules(vararg rules: Rule<Unit>): Unit =
    rules.forEach {
        it.runValidation(
            context = this@applyUnitRules,
            value = Unit,
        )
    }

inline fun ValidationContext.validate(
    condition: Boolean,
    violationGenerator: () -> Violation,
) {
    if (!condition) {
        val violation = violationGenerator()
        onFailure(violation)
    }
}
