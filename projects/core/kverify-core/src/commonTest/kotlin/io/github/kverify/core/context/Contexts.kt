package io.github.kverify.core.context

import kotlin.test.assertFalse
import kotlin.test.assertTrue

object Contexts {
    val successContext =
        object : KVerifyContext {
            override fun validate(
                message: String,
                predicate: Predicate,
            ) {
                assertTrue(predicate(), message)
            }
        }

    val failContext =
        object : KVerifyContext {
            override fun validate(
                message: String,
                predicate: Predicate,
            ) {
                assertFalse(predicate(), message)
            }
        }
}
