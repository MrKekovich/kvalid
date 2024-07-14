package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.context.KValidContext

enum class ValidationStrategy {
    Lazy,
    FailFast,
    CollectAll;

    fun getContext(): KValidContext = when (this) {
        Lazy -> LazyContext()
        FailFast -> FailFastContext()
        CollectAll -> CollectAllContext()
    }
}