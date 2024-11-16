package io.github.kverify.rule

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll

private typealias IntList = List<Int>

class CollectionRulesTest :
    FunSpec({
        test("ofSize must return true") {
            checkAll(Arb.list(Arb.int(), 5..5)) { list ->
                CollectionRules.ofSize<IntList>(5).invoke(list) shouldBe true
            }
        }

        test("ofSize must return false") {
            checkAll(Arb.list(Arb.int(), 1..4)) { list ->
                CollectionRules.ofSize<IntList>(5).invoke(list) shouldBe false
            }
        }

        test("notOfSize must return true") {
            checkAll(Arb.list(Arb.int(), 1..4)) { list ->
                CollectionRules.notOfSize<IntList>(5).invoke(list) shouldBe true
            }
        }

        test("sizeBetween must return true") {
            checkAll(Arb.list(Arb.int(), 3..7)) { list ->
                CollectionRules.sizeBetween<IntList>(3, 7).invoke(list) shouldBe true
            }
        }

        test("sizeBetween must return false") {
            checkAll(Arb.list(Arb.int(), 8..10)) { list ->
                CollectionRules.sizeBetween<IntList>(3, 7).invoke(list) shouldBe false
            }
        }

        test("sizeNotBetween must return true") {
            checkAll(Arb.list(Arb.int(), 8..10)) { list ->
                CollectionRules.sizeNotBetween<IntList>(3, 7).invoke(list) shouldBe true
            }
        }

        test("containsAll must return true") {
            checkAll(Arb.list(Arb.int(), 5..10), Arb.list(Arb.int(), 2..4)) { list, subList ->
                if (list.containsAll(subList)) {
                    CollectionRules.containsAll(subList).invoke(list) shouldBe true
                }
            }
        }

        test("containsNone must return true") {
            checkAll(Arb.list(Arb.int(), 5..10), Arb.list(Arb.int(), 2..4)) { list, otherList ->
                if (otherList.none { it in list }) {
                    CollectionRules.containsNone(otherList).invoke(list) shouldBe true
                }
            }
        }

        test("distinct must return true") {
            checkAll(Arb.list(Arb.int(), 1..10)) { list ->
                if (list.distinct().size == list.size) {
                    CollectionRules.distinct<IntList>().invoke(list) shouldBe true
                }
            }
        }

        test("notEmpty must return true") {
            checkAll(Arb.list(Arb.int(), 1..10)) { list ->
                CollectionRules.notEmpty<IntList>().invoke(list) shouldBe true
            }
        }
    })
