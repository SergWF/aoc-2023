fun day1_1(lines: List<String>): Int  =
    lines.map { line -> line.filter { it.isDigit() } }
        .filter { it.isNotEmpty() }
        .sumOf {"${it.first()}${it.last()}".toInt()}

fun day1_2(lines: List<String>) = lines.map { it.firstDigit() * 10 + it.lastDigit() }.sum()



private val nummap: Map<String, Int> = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)


private fun String.firstDigit(): Int {
    var n = this.indexOfFirst {it.isDigit()}
    if(n == -1) n = Int.MAX_VALUE
    val (i, s) = this.findAnyOf(nummap.keys) ?: Pair(Int.MAX_VALUE, "")
    if(n == i && i == Int.MAX_VALUE) return 0
    return if(n < i) this[n].digitToInt() else nummap[s]!!
}

private fun String.lastDigit(): Int {
    val n = this.indexOfLast { it.isDigit() }
    val (i, s) = this.findLastAnyOf(nummap.keys) ?: Pair(-1, "")
    if(n == i && i == -1) return 0
    return if(n > i) this[n].digitToInt() else nummap[s]!!
}
