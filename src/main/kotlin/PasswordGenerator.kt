@file:Suppress("Unused")

package com.origincoding

import org.apache.commons.text.RandomStringGenerator

inline fun <T> MutableList<T>.addAllIf(
    vararg element: T, condition: () -> Boolean
) {
    if (condition()) {
        this.addAll(element)
    }
}

/**
 * 构造随机字符生成器
 *
 * @param minLength 密码最小长度
 * @param maxLength 密码最大长度
 * @param characterOptions 字符集选项
 * @param count 生成密码数量
 *
 * @return 构造出的RandomStringGenerator
 *
 * @see CharacterOptions
 * @see RandomStringGenerator
 */
internal fun buildGenerator(
    minLength: Int, maxLength: Int, characterOptions: CharacterOptions, count: Int
): RandomStringGenerator {
    // 对密码长度进行校验
    require(minLength > 3) { "最小长度不能小于4！" }
    require(maxLength < 33) { "最大长度不能大于32！" }
    require(minLength <= maxLength) { "最大长度不能小于最小长度！" }

    // 对字符集类型进行校验
    require(characterOptions.characterTypes > 1) { "至少选择两种类型的字符！" }

    // 对生成的密码数量进行校验
    require(count > 0) { "密码数量不能小于0！" }

    // 确定使用的字符集
    val charArrays = mutableListOf<CharArray>()
    charArrays.addAllIf(charArrayOf('a', 'z')) { characterOptions.lowercase }
    charArrays.addAllIf(charArrayOf('A', 'Z')) { characterOptions.uppercase }
    charArrays.addAllIf(charArrayOf('0', '9')) { characterOptions.digits }
    charArrays.addAllIf(
        charArrayOf('!', '!'),
        charArrayOf('#', '&'),
        charArrayOf('*', '/'),
        charArrayOf(':', '@'),
        charArrayOf('^', '^'),
        charArrayOf('|', '|'),
        charArrayOf('~', '~'),
    ) {
        characterOptions.specialChars
    }

    // 构造Generator，生成密码
    return RandomStringGenerator.builder().withinRange(*charArrays.toTypedArray<CharArray>()).get()
}

/**
 * 对生成的密码进行校验，在characterOptions中允许的字符集要在生成的密码中出现
 *
 * @param password 生成的密码
 * @param characterOptions 字符集选项
 */
internal fun passwordIsValid(password: String, characterOptions: CharacterOptions): Boolean {
    return password.let {
        (it.any { char -> char.isUpperCase() } || !characterOptions.uppercase) && (it.any { char -> char.isLowerCase() } || !characterOptions.lowercase) && (it.any { char -> char.isDigit() } || !characterOptions.digits) && (it.any { char -> !char.isLetterOrDigit() } || !characterOptions.specialChars)
    }
}

/**
 * 生成多个密码
 *
 * @param minLength 密码最小长度，默认为8
 * @param maxLength 密码最大长度，默认为16
 * @param characterOptions 字符集选项
 * @param count 生成密码数量
 *
 * @return 生成的密码列表
 *
 * @see CharacterOptions
 */
fun generatePassword(
    minLength: Int = 8, maxLength: Int = 16, characterOptions: CharacterOptions = CharacterOptions(), count: Int = 1
): List<String> {
    val generator = buildGenerator(minLength, maxLength, characterOptions, count)

    return generateSequence {
        generator.generate(minLength, maxLength)
    }.filter {
        passwordIsValid(it, characterOptions)
    }.take(count).toList()
}

/**
 * 生成单个密码
 *
 * @param minLength 密码最小长度，默认为8
 * @param maxLength 密码最大长度，默认为16
 * @param characterOptions 字符集选项
 *
 * @return 生成的密码
 *
 * @see CharacterOptions
 */
fun generatePassword(
    minLength: Int = 8, maxLength: Int = 16, characterOptions: CharacterOptions = CharacterOptions()
): String {
    val generator = buildGenerator(minLength, maxLength, characterOptions, 1)
    return generateSequence {
        generator.generate(minLength, maxLength)
    }.filter {
        passwordIsValid(it, characterOptions)
    }.first()
}

/**
 * 生成多个固定长度的密码
 *
 * @param length 密码长度，要求在8~32之间
 * @param characterOptions 字符集选项
 * @param count 密码数量
 *
 * @return 生成的密码列表
 */
fun generatePassword(
    length: Int = 16, characterOptions: CharacterOptions = CharacterOptions(), count: Int = 1
): List<String> = generatePassword(length, length, characterOptions, count)

/**
 * 生成一个指定长度的密码
 *
 * @param length 密码长度，要求在8~32之间
 * @param characterOptions 字符集选项
 *
 * @return 生成的单个密码
 */
fun generatePassword(
    length: Int = 16, characterOptions: CharacterOptions = CharacterOptions()
): String = generatePassword(length, length, characterOptions)

