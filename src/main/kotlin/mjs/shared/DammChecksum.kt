package mjs.shared

object DammChecksum {

    /**
     * The quasigroup table from https://en.wikipedia.org/wiki/Damm_algorithm
     */
    private val table = arrayOf(
        intArrayOf(0, 3, 1, 7, 5, 9, 8, 6, 4, 2),
        intArrayOf(7, 0, 9, 2, 1, 5, 4, 8, 6, 3),
        intArrayOf(4, 2, 0, 6, 8, 7, 1, 3, 5, 9),
        intArrayOf(1, 7, 5, 0, 9, 8, 3, 4, 2, 6),
        intArrayOf(6, 1, 2, 3, 0, 4, 5, 9, 7, 8),
        intArrayOf(3, 6, 7, 4, 2, 0, 9, 5, 8, 1),
        intArrayOf(5, 8, 6, 9, 7, 2, 0, 1, 3, 4),
        intArrayOf(8, 9, 4, 5, 3, 6, 2, 0, 1, 7),
        intArrayOf(9, 4, 3, 8, 6, 1, 7, 2, 0, 5),
        intArrayOf(2, 5, 8, 1, 4, 3, 6, 7, 9, 0)
    )

    private fun calcCheckDigit(num: String, idx: Int): Int =
        if (num.isEmpty()) idx
        else calcCheckDigit(num.drop(1), table[idx][num.first() - '0'])

    fun checkDigit(numberString: String): Int {
        return calcCheckDigit(numberString, 0)
    }

    fun checkDigit(number: Int): Int {
        return checkDigit(number.toString())
    }

    fun checkSum(number: String): String {
        val checkSumDigit = checkDigit(number)
        return number + checkSumDigit.toString()
    }

    fun checkSum(number: Int): Int {
        val checkSumDigit = checkDigit(number)
        return number * 10 + checkSumDigit
    }

    fun isValid(number: String): Boolean {
        return checkDigit(number) == 0
    }

    fun isValid(number: Int): Boolean {
        return checkDigit(number) == 0
    }
}