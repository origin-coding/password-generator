package com.origincoding

import org.apache.commons.text.RandomStringGenerator

fun generatePassword(
    minLength: Int = 8,
    maxLength: Int = 16,
    characterOptions: CharacterOptions = CharacterOptions(),
    count: Int = 1
): List<String> {
    // 对密码长度进行校验
    require(minLength > 3) { "最小长度不能小于4！" }
    require(maxLength < 33) { "最大长度不能大于32！" }
    require(minLength <= maxLength) { "最大长度不能小于最小长度！" }

    // 对字符集类型进行校验
    require(characterOptions.characterTypes() > 1) { "至少选择两种类型的字符！" }

    // 对生成的密码数量进行校验
    require(count > 0) { "密码数量不能小于0！" }

    val charArrays = mutableListOf<CharArray>()
    if (characterOptions.uppercase) {
        charArrays.add(charArrayOf('A', 'Z'))
    }
    if (characterOptions.lowercase) {
        charArrays.add(charArrayOf('a', 'z'))
    }
    if (characterOptions.digits) {
        charArrays.add(charArrayOf('0', '9'))
    }
    if (characterOptions.specialChars) {
        charArrays.add(charArrayOf('!', '!'))
        charArrays.add(charArrayOf('#', '&'))
        charArrays.add(charArrayOf('*', '/'))
        charArrays.add(charArrayOf(':', '@'))
        charArrays.add(charArrayOf('^', '^'))
        charArrays.add(charArrayOf('|', '|'))
        charArrays.add(charArrayOf('~', '~'))
    }

    val generator = RandomStringGenerator.builder().withinRange(*charArrays.toTypedArray()).get()

    val list = mutableListOf<String>()
    for (i in 1..count) {
        val password = generator.generate(minLength, maxLength)
        list.add(password)
    }

    return list
}
