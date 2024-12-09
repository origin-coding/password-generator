@file:Suppress("Unused")

package functional

import com.origincoding.generatePassword
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldHaveMaxLength
import io.kotest.matchers.string.shouldHaveMinLength

class PasswordLengthTest : FunSpec({
    test("生成单个密码，固定长度下的长度需要符合要求") {
        generatePassword(length = 8) shouldHaveLength 8
    }

    test("生成多个固定长度的密码，它们的长度应该一致") {
        generatePassword(length = 12, count = 5).forAll {
            it shouldHaveLength 12
        }
    }

    test("生成单个长度在一定范围的密码，长度需要符合要求") {
        generatePassword(8, 32) shouldHaveMinLength 8 shouldHaveMaxLength 32
    }

    test("生成多个长度在一定范围的密码，它们的长度需要符合要求") {
        generatePassword(minLength = 8, maxLength = 32, count = 5).forAll {
            it shouldHaveMinLength 8 shouldHaveMaxLength 32
        }
    }

    test("生成密码时，密码长度需要在4~32之间") {
        shouldThrow<IllegalArgumentException> { generatePassword(length = 3) }
        shouldThrow<IllegalArgumentException> { generatePassword(length = 33) }

        shouldThrow<IllegalArgumentException> { generatePassword(length = 3, count = 10) }
        shouldThrow<IllegalArgumentException> { generatePassword(length = 33, count = 10) }
    }

    test("生成密码的最小长度不能大于最大长度") {
        shouldThrow<IllegalArgumentException> { generatePassword(minLength = 9, maxLength = 8, count = 10) }
        shouldThrow<IllegalArgumentException> { generatePassword(minLength = 9, maxLength = 8) }
    }
})