package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

@JvmInline
value class AnyViolation(
    override val message: String,
) : Violation {
    override fun toString(): String = "AnyViolation(message=$message)"
}

inline fun String.asViolation(): Violation = AnyViolation(this)
