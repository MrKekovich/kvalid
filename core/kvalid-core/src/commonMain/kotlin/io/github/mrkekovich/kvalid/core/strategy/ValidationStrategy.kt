package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.context.KValidContext

enum class ValidationStrategy {
    FailFast,
    CollectAll;

    fun getContext(): KValidContext = when (this) {
        FailFast -> FailFastContext()
        CollectAll -> CollectAllContext()
    }
}