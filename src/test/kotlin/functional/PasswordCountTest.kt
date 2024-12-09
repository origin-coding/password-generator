@file:Suppress("Unused")

package functional

import com.origincoding.generatePassword
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class PasswordCountTest : FunSpec({
    test("生成一定范围长度的固定数量密码，数量需要符合要求") {
        generatePassword(minLength = 8, maxLength = 32, count = 10) shouldHaveSize 10
    }

    test("生成长度固定的多个密码，数量需要符合要求") {
        generatePassword(length = 10, count = 10) shouldHaveSize 10
    }

    test("密码长度不能小于1") {
        val exception = shouldThrow<IllegalArgumentException> {
            generatePassword(count = 0)
        }

        exception.message shouldBe "密码数量不能小于0！"
    }
})
