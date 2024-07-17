package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.context.KValidContext

enum class ValidationStrategy {
    Aggregate,
    Immediate;

    fun getContext(): KValidContext = when (this) {
        Aggregate -> AggregateValidationContext()
        Immediate -> ImmediateValidationContext()
    }
}