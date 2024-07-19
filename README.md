<p align="center">
  <img src="readme/kvalid-logo.svg" alt="KValid Logo">
</p>

<h1 align="center">KValid</h1>

<p align="center">
  <strong>A powerful, flexible Kotlin Multiplatform validation library</strong>
</p>

<p align="center">
  <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/kotlin-2.0.0-blue.svg?logo=kotlin" alt="Kotlin 2.0.0"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License"></a>
  <img src="https://img.shields.io/badge/Version-0.1.0--alpha-orange" alt="Version 0.1.0-alpha">
  <img src="https://img.shields.io/badge/Status-In%20Development-yellow" alt="Status: In Development">
</p>

---

## 🚀 Overview

KValid is a Kotlin Multiplatform library that provides a concise and powerful DSL for validation. It offers standard use cases and the flexibility to create custom validation rules, making it suitable for a wide range of applications.

⚠️ **Note:** KValid is currently in alpha. The API is subject to change.

## ✨ Features

- 🌟 Concise and intuitive DSL
- 🔧 Flexible rule creation
- 📦 [Standard validation use cases included](readme/standard-use-cases.md).
- 🌍 Kotlin Multiplatform support
- 🧩 Extensible architecture

## 🛠 Installation

KValid is not yet published to Maven Central. Stay tuned for release information!

## 🎯 Quick Start

Here's a simple example of how to use KValid:

```kotlin
import io.github.mrkekovich.kvalid.*

val database = mutableSetOf<User>()
object TimeSingleton {
    val hour: Int get() = TODO()
}
val globalState = true
// -- Custom rules --
// Simple rule with message
val isRightTime = createRule("It must be evening") { TimeSingleton.hour >= 18 }

// Rule that takes value
val mustStartWithCapitalLetter = createRule<String>("Given value must start with capital letter") { it.first() == it.first().uppercase() }

// Custom rule with message generation, depending on the name and value
val nameIsInDatabaseRule = createRule<String>(
    message = { "${it.value} must be in database. Name '${it.value}' was not found." },
    predicate = { name: String ->
        database.any { it.name == name }
    }
)

data class User(val name: String, val age: Int) {
    var count = 0
    // -- Custom validations --
    fun validateAndCollect(): ValidationResult = validateAll {
        "name".withValue(name)
            .notBlank()
            .lengthOf(3..20)
            .validate(isRightTime)
            .validate(mustStartWithCapitalLetter)
            .validate(nameIsInDatabaseRule)
        
        /*this*/::age.toNamed(value = age)
            .greaterThan(18)
            .lessThan(65)
        
        // jvm supports reflection, so we can use toNamed without providing any value:
        // ::age.toNamed().greaterThan(18).lessThan(65)
        
        if (globalState) {
            violation("Global state is enabled")
        }
    }
        .onInvalid { violations: List<ValidationException> -> println("Validation failed: ${violations.count()}") }
        .onValid { println("Validation succeeded") }
    
    fun validateAndThrow(): Unit = throwOnFailure {
        "name".withValue(name)
            .notBlank() // But throws ValidationException with meaningful message: "name must not be blank"
            .lengthOf(5) // Will not execute the rest of the rules if one fails
    }
    
    fun validateFailFast(): ValidationResult = validateFirst {
        "name".withValue(name)
            .notBlank() // if one rule fails, returns ValidationResult immediately.
            .lengthOf(3..20) // won't execute the rest of the rules if one fails
    } 
    
    fun validateLazily(): Sequence<ValidationException> = validateLazy {
        "name".withValue(name)
            .notBlank()
            .lengthOf(3..20)
            .validate(isRightTime)
            .validate(mustStartWithCapitalLetter)
            .validate(nameIsInDatabaseRule)
        
        rule("dummy rule") {
            count++ // won't be executed until we access the sequence
            false
        }
    }
}

fun main() {
    val user = User("John", 18)
    user.validateAndCollect() // returns ValidationResult.violations: ["name must be in database. Name 'John' was not found.", "age must be greater than 18"]
    user.validateAndThrow() // throws ValidationException("name must be in database. Name 'John' was not found.")
    user.validateFailFast() // returns ValidationResult.violations: ["name must be in database. Name 'John' was not found."]
    val lazy = user.validateLazily() 
    println(user.count) // 0
    lazy.forEach { 
        // First, we meet the database rule, which won't increment the count.
        if (it.message == "dummy rule") {
            // Second, we meet the dummy rule, which will execute the predicate and thus increment the count.
            println("dummy rule was executed")
            println(user.count) // 1
        } else {
            println("other rule was executed")
            println(user.count) // 0
        }
    }
}
```
## 📘 Documentation

Comprehensive documentation and tutorials are coming soon! We'll provide a detailed guide on how to use KValid effectively in your projects.

For more examples and use cases, check out our [documentation](readme/documentation.md).

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

KValid is released under the [Apache 2.0 License](LICENSE).

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/mrkekovich">MrKekovich</a>
</p>