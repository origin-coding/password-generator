package com.origincoding

/**
 * 生成密码时的字符集选项
 *
 * @property uppercase 是否包含大写字母
 * @property lowercase 是否包含小写字母
 * @property digits 是否包含数字
 * @property specialChars 是否包含特殊字符
 */
data class CharacterOptions(
    val uppercase: Boolean = true,
    val lowercase: Boolean = true,
    val digits: Boolean = true,
    val specialChars: Boolean = true
) {
    /**
     * 字符集类型的数量
     *
     * @return Int 字符集类型的数量
     */
    val characterTypes: Int by lazy {
        arrayOf(specialChars, uppercase, lowercase, digits).count { it }
    }
}
