<p align="center">
  <img src="docs/img/kverify-logo.svg" alt="KVerify Logo">
</p>

<p align="center">
  <strong>A powerful, flexible Kotlin Multiplatform validation library</strong>
</p>

<p align="center">
  <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/kotlin--multiplatform-2.0.0-blue.svg?logo=kotlin" alt="Kotlin 2.0.0"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License"></a>
  <img src="https://img.shields.io/badge/Version-1.6.0-blue" alt="Version 1.6.0">
</p>

---

## 🚀 Overview

KVerify is a modern Kotlin Multiplatform validation library that provides a concise and powerful DSL for defining and executing validation rules.  
Whether you're validating simple values or complex object hierarchies, KVerify makes it easy while maintaining type safety and flexibility.

## ✨ Key Features

- 🌟 Intuitive DSL for defining validation rules  
- 🔧 Multiple validation strategies (immediate, aggregate, lazy)  
- 🎯 Type-safe validation rules  
- 🌍 Kotlin Multiplatform support  
- 📦 Modular architecture with core and rules packages  
- 🔄 Composable validation rules  
- 🏷️ Named value support for better error messages  
- 🎨 Flexible API for custom validation logic  
- 🌐 Built-in localization framework for standard rule sets  

## 🛠 Installation

### Groovy
```groovy
dependencies {
    implementation "io.github.kverify:kverify-core:${version}"
    implementation "io.github.kverify:kverify-rules:${version}" // Optional: standard rule sets
}
```

### Kotlin
```kotlin
dependencies {
    implementation("io.github.kverify:kverify-core:${version}")
    implementation("io.github.kverify:kverify-rules:${version}")  // Optional: standard rule sets
}
```

## 📝 Quick Start

```kotlin
import io.github.kverify.dsl.extension.toNamed
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.dsl.model.unwrapOrNull
import io.github.kverify.dsl.model.withName
import io.github.kverify.dsl.validator.validateAll
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.named.NamedStringRules

// 1. Define your data class
data class User(
    val name: String,
    val email: String?,
    val age: Int,
)

// 2. Create standard rule sets
val stringRules = NamedStringRules(DefaultRuleLocalization())

// 3. Define validation rules
val emailRule =
    createNamedRule<String> { (name, value) ->
        validate("$name has invalid email format") { value.contains("@") }
    }

val userRules =
    createNamedRule<User> { (name, user) ->
        user.run {
            // Validate name
            ::name.toNamed().validate(
                stringRules.notBlank(),
                stringRules.lengthBetween(2..50),
            )

            // Validate optional email
            ::email.toNamed().unwrapOrNull()?.validate(emailRule)
        }
    }

// 4. Validate an instance
val user =
    User(
        name = "John",
        email = "john@example.com",
        age = 25,
    )

val result =
    validateAll {
        user.withName("user").validate(userRules)
    }

// 5. Handle the results
fun main() {
    result
        .onValid {
            println("User is valid!")
        }.onInvalid { errors ->
            println("Validation failed:")
            errors.forEach { println(it) }
        }
}
```

## 📖 Documentation

The [KVerify Wiki](https://github.com/KVerify/kverify/wiki) is available to guide you through installation, usage, and advanced features.
⚠️ Please note that the wiki is still a work in progress — some pages might be incomplete or missing.

## 📄 License

KVerify is released under the [Apache 2.0 License](LICENSE).

---

<p align="center"> Made with ❤️ by <a href="https://github.com/mrkekovich">MrKekovich</a> </p>
