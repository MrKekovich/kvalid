package io.github.kverify.core.context

import kotlin.test.assertFalse
import kotlin.test.assertTrue

object Contexts {
    val successContext =
        object : ValidationContext {
            override fun validate(
                message: String,
                predicate: Predicate,
            ) {
                assertTrue(predicate(), message)
            }
        }

    val failContext =
        object : ValidationContext {
            override fun validate(
                message: String,
                predicate: Predicate,
            ) {
                assertFalse(predicate(), message)
            }
        }
}
