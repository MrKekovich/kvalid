package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

@JvmInline
@PublishedApi
internal value class AnyViolation(
    override val message: String,
) : Violation {
    override fun toString(): String = "AnyViolation(message=$message)"
}
