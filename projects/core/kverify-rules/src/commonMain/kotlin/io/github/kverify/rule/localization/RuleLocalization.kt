package io.github.kverify.rule.localization

import io.github.kverify.core.model.NamedValue
import io.github.kverify.rule.type.RuleType

interface RuleLocalization {
    fun getLocalization(
        key: RuleType,
        namedValue: NamedValue<*>,
    ): String
}
