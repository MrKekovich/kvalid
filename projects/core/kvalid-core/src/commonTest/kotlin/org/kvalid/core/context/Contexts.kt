package org.kvalid.core.context

import kotlin.test.assertFalse
import kotlin.test.assertTrue

object Contexts {
    val successContext = object : KValidContext {
        override fun validate(message: String, predicate: Predicate) {
            assertTrue(predicate(), message)
        }
    }

    val failContext = object : KValidContext {
        override fun validate(message: String, predicate: Predicate) {
            assertFalse(predicate(), message)
        }
    }
}