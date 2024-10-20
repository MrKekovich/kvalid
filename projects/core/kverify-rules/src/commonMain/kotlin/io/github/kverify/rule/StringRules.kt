package io.github.kverify.rule

private typealias StringPredicate = (String) -> Boolean

@Suppress("TooManyFunctions")
object StringRules {
    fun ofLength(length: Int): StringPredicate =
        {
            it.length == length
        }

    fun notOfLength(length: Int): StringPredicate =
        {
            it.length != length
        }

    fun maxLength(max: Int): StringPredicate =
        {
            it.length <= max
        }

    fun minLength(min: Int): StringPredicate =
        {
            it.length >= min
        }

    fun lengthBetween(range: IntRange): StringPredicate =
        {
            it.length in range
        }

    fun lengthNotBetween(range: IntRange): StringPredicate =
        {
            it.length !in range
        }

    fun lengthBetween(
        min: Int,
        max: Int,
    ): StringPredicate =
        lengthBetween(
            min..max,
        )

    fun lengthNotBetween(
        min: Int,
        max: Int,
    ): StringPredicate =
        lengthNotBetween(
            min..max,
        )

    fun contains(string: String): StringPredicate =
        {
            it.contains(string)
        }

    fun notContains(string: String): StringPredicate =
        {
            !it.contains(string)
        }

    fun contains(char: Char): StringPredicate =
        {
            it.contains(char)
        }

    fun notContains(char: Char): StringPredicate =
        {
            !it.contains(char)
        }

    fun containsAll(chars: Set<Char>): StringPredicate =
        {
            it.all { char -> char in chars }
        }

    fun containsOnly(allowedChars: Set<Char>): StringPredicate =
        {
            it.all { char -> char in allowedChars }
        }

    fun containsNone(prohibitedChars: Set<Char>): StringPredicate =
        {
            it.none { char -> char in prohibitedChars }
        }

    fun matches(regex: Regex): StringPredicate =
        {
            regex.matches(it)
        }

    fun notMatches(regex: Regex): StringPredicate =
        {
            !regex.matches(it)
        }

    fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
    ): StringPredicate =
        {
            it.startsWith(prefix, ignoreCase)
        }

    fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
    ): StringPredicate =
        {
            it.endsWith(suffix, ignoreCase)
        }

    val alphabetic: StringPredicate = {
        it.all { char -> char.isLetter() }
    }

    val alphanumeric: StringPredicate = { str ->
        str.all { char -> char.isLetterOrDigit() }
    }

    val notBlank: StringPredicate = {
        it.isNotBlank()
    }

    val notEmpty: StringPredicate = {
        it.isNotEmpty()
    }

    val lowerCase: StringPredicate = {
        it.all { char -> char.isLowerCase() }
    }

    val upperCase: StringPredicate = {
        it.all { char -> char.isUpperCase() }
    }

    val numeric: StringPredicate = {
        it.all { char -> char.isDigit() }
    }
}
