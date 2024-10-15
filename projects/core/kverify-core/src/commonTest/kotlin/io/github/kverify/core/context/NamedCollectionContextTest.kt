package io.github.kverify.core.context

import io.github.kverify.core.context.Contexts.failContext
import io.github.kverify.core.context.Contexts.successContext
import io.github.kverify.dsl.model.withName
import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll

class NamedCollectionContextTest :
    FunSpec({

        test("ofSize(Int)") {
            val size = 5

            checkAll(Arb.list(Arb.int(), size..size)) { value ->
                successContext.run {
                    value.withName("list").ofSize(size)
                }
            }

            checkAll(Arb.list(Arb.int()).filter { it.size != size }) { value ->
                failContext.run {
                    value.withName("list").ofSize(size)
                }
            }
        }

        test("ofSize(IntRange)") {
            val range = 5..10

            checkAll(Arb.list(Arb.int(), range)) { value ->
                successContext.run {
                    value.withName("list").ofSize(range)
                }
            }

            checkAll(Arb.list(Arb.int()).filter { it.size !in range }) { value ->
                failContext.run {
                    value.withName("list").ofSize(range)
                }
            }
        }

        test("notOfSize(Int)") {
            val size = 5

            checkAll(Arb.list(Arb.int()).filter { it.size != size }) { value ->
                successContext.run {
                    value.withName("list").notOfSize(size)
                }
            }

            checkAll(Arb.list(Arb.int(), size..size)) { value ->
                failContext.run {
                    value.withName("list").notOfSize(size)
                }
            }
        }

        test("notOfSize(IntRange)") {
            val range = 5..10

            checkAll(Arb.list(Arb.int()).filter { it.size !in range }) { value ->
                successContext.run {
                    value.withName("list").notOfSize(range)
                }
            }

            checkAll(Arb.list(Arb.int(), range)) { value ->
                failContext.run {
                    value.withName("list").notOfSize(range)
                }
            }
        }

        test("minSize") {
            val min = 5

            checkAll(Arb.list(Arb.int(), min..100)) { value ->
                successContext.run {
                    value.withName("list").minSize(min)
                }
            }

            checkAll(Arb.list(Arb.int(), 0..<min)) { value ->
                failContext.run {
                    value.withName("list").minSize(min)
                }
            }
        }

        test("maxSize") {
            val max = 5

            checkAll(Arb.list(Arb.int(), 0..max)) { value ->
                successContext.run {
                    value.withName("list").maxSize(max)
                }
            }

            checkAll(Arb.list(Arb.int(), (max + 1)..100)) { value ->
                failContext.run {
                    value.withName("list").maxSize(max)
                }
            }
        }

        test("contains") {
            val list = listOf(1, 2, 3)

            successContext.run {
                list.withName("list").contains(list[0])
            }

            failContext.run {
                list.withName("list").contains(4)
            }
        }

        test("notContains") {
            val list = listOf(1, 2, 3)

            successContext.run {
                list.withName("list").notContains(4)
            }

            failContext.run {
                list.withName("list").notContains(list[0])
            }
        }

        test("containsAll") {
            val list = listOf(1, 2, 3)
            val otherList = list + 4

            successContext.run {
                list.withName("list").containsAllOf(list)
            }

            failContext.run {
                list.withName("list").containsAllOf(otherList)
            }
        }

        test("notContainsAllOf") {
            val list = listOf(1, 2, 3)
            val otherList = list + 4

            successContext.run {
                list.withName("list").notContainsAllOf(otherList)
            }

            failContext.run {
                list.withName("list").notContainsAllOf(list)
            }
        }

        test("containsNoneOf") {
            val list = listOf(1, 2, 3)
            val partlyContains = list + 4
            val unique = listOf(10, 20, 30)

            successContext.run {
                list.withName("list").containsNoneOf(unique)
            }

            failContext.run {
                list
                    .withName("list")
                    .containsNoneOf(partlyContains)
                    .containsNoneOf(list)
            }
        }

        test("allMatch") {
            val list = listOf(1, 2, 3)

            successContext.run {
                list.withName("list").allMatch { it > 0 }
            }

            failContext.run {
                list.withName("list").allMatch { it < 0 }
            }
        }

        test("anyMatch") {
            val list = listOf(1, 2, 3)

            successContext.run {
                list.withName("list").anyMatch { it == list[0] }
            }

            failContext.run {
                list.withName("list").anyMatch { it == 0 }
            }
        }

        test("noneMatch") {
            val list = listOf(1, 2, 3)

            successContext.run {
                list.withName("list").noneMatch { it == 0 }
            }

            failContext.run {
                list.withName("list").noneMatch { it == list[0] }
            }
        }
    })
