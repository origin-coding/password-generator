package com.origincoding

fun generateRandomPassword(): String {
    val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..8)
        .map { chars.random() }
        .joinToString("")
}

fun main() {
    repeat(5) {
        println(generateRandomPassword())
    }
}