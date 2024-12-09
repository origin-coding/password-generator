@file:Suppress("Unused")

package functional

import com.origincoding.CharacterOptions
import com.origincoding.generatePassword
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldNotBeEmpty

class CharacterTypeTest : FunSpec({
    test("字符集至少有两种时，能够生成密码") {
        generatePassword(characterOptions = CharacterOptions(uppercase = false, lowercase = false)).shouldNotBeEmpty()
    }

    test("只有一种字符集时，将会报错") {
        shouldThrow<IllegalArgumentException> {
            generatePassword(
                characterOptions = CharacterOptions(
                    uppercase = false,
                    lowercase = false,
                    digits = false,
                    specialChars = true
                )
            )
        }
    }
})
