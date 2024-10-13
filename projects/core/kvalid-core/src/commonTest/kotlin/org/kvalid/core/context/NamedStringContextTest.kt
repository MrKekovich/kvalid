package org.kvalid.core.context

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.exhaustive
import org.kvalid.core.context.Contexts.failContext
import org.kvalid.core.context.Contexts.successContext
import org.kvalid.dsl.model.withName

internal class NamedStringContextTest : FunSpec({
    test("notBlank") {
        checkAll(Arb.string().filter { it.isNotBlank() }) { value ->
            successContext.run {
                value.withName("not blank").notBlank()
            }
        }

        checkAll(Arb.blankString()) { value ->
            failContext.run {
                value.withName("blank").notBlank()
            }
        }
    }

    test("ofLength(Int)") {
        val length = 5

        checkAll(Arb.string(length)) { lengthString ->
            successContext.run {
                lengthString.withName("test").ofLength(length)
            }
        }

        checkAll(Arb.string().filter { it.length != length }) { value ->
            failContext.run {
                value.withName("test").ofLength(length)
            }
        }
    }

    test("ofLength(range)") {
        val length = 5..10

        checkAll(Arb.string(length)) { lengthString ->
            successContext.run {
                lengthString.withName("test").ofLength(length)
            }
        }

        checkAll(Arb.string().filter { it.length !in length }) { value ->
            failContext.run {
                value.withName("test").ofLength(length)
            }
        }
    }

    test("notOfLength(Int)") {
        val length = 5

        checkAll(Arb.string().filter { it.length != length }) { value ->
            successContext.run {
                value.withName("test").notOfLength(length)
            }
        }

        checkAll(Arb.string(length)) { lengthString ->
            failContext.run {
                lengthString.withName("test").notOfLength(length)
            }
        }
    }

    test("notOfLength(range)") {
        val length = 5..10

        checkAll(Arb.string().filter { it.length !in length }) { value ->
            successContext.run {
                value.withName("test").notOfLength(length)
            }
        }

        checkAll(Arb.string(length)) { value ->
            failContext.run {
                value.withName("test").notOfLength(length)
            }
        }
    }

    test("minLength") {
        val minLength = 5

        checkAll(Arb.string(minLength..100)) { value ->
            successContext.run {
                value.withName("test").minLength(minLength)
            }
        }

        checkAll(Arb.string(0..<minLength)) { value ->
            failContext.run {
                value.withName("test").minLength(minLength)
            }
        }
    }

    test("maxLength") {
        val maxLength = 5

        checkAll(Arb.string(0..maxLength).filter { it.length <= maxLength }) { value ->
            successContext.run {
                value.withName("test").maxLength(maxLength)
            }
        }

        checkAll(Arb.string(minSize = maxLength + 1)) { value ->
            failContext.run {
                value.withName("test").maxLength(maxLength)
            }
        }
    }

    val digitsOnlyRegex = Regex("^[0-9]+$")

    val passingStrings = listOf("123", "45", "789")
    val failingStrings = listOf("abc", "123.4", "12 3", "12-3", "1a2")

    test("matches(Regex)") {
        passingStrings.forEach { value ->
            successContext.run {
                value.withName("test").matches(digitsOnlyRegex)
            }
        }

        failingStrings.forEach { value ->
            failContext.run {
                value.withName("test").matches(digitsOnlyRegex)
            }
        }
    }

    test("matches(String)") {
        passingStrings.forEach { value ->
            successContext.run {
                value.withName("test").matches(digitsOnlyRegex.pattern)
            }
        }

        failingStrings.forEach { value ->
            failContext.run {
                value.withName("test").matches(digitsOnlyRegex.pattern)
            }
        }
    }

    test("notMatches(Regex)") {
        failingStrings.forEach { value ->
            successContext.run {
                value.withName("test").notMatches(digitsOnlyRegex)
            }
        }

        passingStrings.forEach { value ->
            failContext.run {
                value.withName("test").notMatches(digitsOnlyRegex)
            }
        }
    }

    test("notMatches(String)") {
        failingStrings.forEach { value ->
            successContext.run {
                value.withName("test").notMatches(digitsOnlyRegex.pattern)
            }
        }

        passingStrings.forEach { value ->
            failContext.run {
                value.withName("test").notMatches(digitsOnlyRegex.pattern)
            }
        }
    }
})

/**
 * Generates a blank string of length between [minLength] and [maxLength].
 * Blank string will contain random characters from [whitespaceChars].
 *
 *
 * @param minLength min length.
 * @param maxLength max length.
 */
private fun Arb.Companion.blankString(minLength: Int = 0, maxLength: Int = 100): Arb<String> {
    val size = maxLength - minLength

    return List(size) {
        (minLength..maxLength).map { whitespaceChars.random() }.joinToString("")
    }.exhaustive().toArb()
}

private val whitespaceChars = listOf(
    '\u0009',
    '\u000A',
    '\u000B',
    '\u000C',
    '\u000D',
    '\u001C',
    '\u001D',
    '\u001E',
    '\u001F',
    '\u0020',
    '\u00A0',
    '\u1680',
    '\u2000',
    '\u2001',
    '\u2002',
    '\u2003',
    '\u2004',
    '\u2005',
    '\u2006',
    '\u2007',
    '\u2008',
    '\u2009',
    '\u200A',
    '\u2028',
    '\u2029',
    '\u202F',
    '\u205F',
    '\u3000'
)