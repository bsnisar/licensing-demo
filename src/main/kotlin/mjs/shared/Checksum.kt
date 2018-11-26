package mjs.shared

interface Checksum {
    fun checkDigit(numberString: String): Int
    fun checkDigit(number: Int): Int
    fun checkSum(numberString: String): String
    fun checkSum(number: Int): Int
    fun isValid(numberString: String): Boolean
    fun isValid(number: Int): Boolean
}