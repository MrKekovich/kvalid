package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.Rule
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal object Contexts {
    val successContext = object : KValidContext {
        override fun validate(rule: Rule) {
            assertTrue(rule.validate(), "Rule failed: ${rule.failMessage}")
        }
    }

    val failContext = object : KValidContext {
        override fun validate(rule: Rule) {
            assertFalse(rule.validate(), "Rule unexpectedly passed: ${rule.failMessage}")
        }
    }
}
