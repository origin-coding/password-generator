package com.origincoding

data class CharacterOptions(
    val uppercase: Boolean = true,
    val lowercase: Boolean = true,
    val digits: Boolean = true,
    val specialChars: Boolean = true
) {
    fun characterTypes(): Int {
        return arrayOf(specialChars, uppercase, lowercase, digits).count { it }
    }
}
