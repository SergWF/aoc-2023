import kotlin.math.max
import kotlin.math.min

fun day3_1(lines: List<String>) =
    lines.mapIndexed { lineIndex, line -> findNumbers(line, lines.getNeighborLines(lineIndex)) }
        .flatten()
        .filter { it.second.isNotEmpty() }
        .sumOf { it.first }


fun day3_2(lines: List<String>): Int =
    lines.mapIndexed { lineIndex, line -> findGears(line, lines.getNeighborLines(lineIndex)) }
        .flatten()
        .filter { it.size >= 2 }
        .map { gear -> gear.fold(1) { acc, g -> acc.times(g.toInt()) } }
        .sum()


fun findGears(line: String, neighborLines: List<String>): List<List<String>> =
    line.foldIndexed(mutableListOf<Int>()) { idx, acc, c ->
        if (c == '*') {
            acc.add(idx)
        }; acc
    }.map { star -> getNumbersAround(star, neighborLines) }.filter { it.isNotEmpty() }


fun getNumbersAround(idx: Int, lines: List<String>): List<String> {
    val nums = mutableListOf<String>()
    lines.forEach { line ->
        if (line[idx].isDigit()) {
            nums.add(goLeft(line, idx) + line[idx] + goRight(line, idx))
        } else {
            if (idx > 0 && line[idx - 1].isDigit()) {
                nums.add(goLeft(line, idx) + line[idx])
            }
            if (idx < line.lastIndex && line[idx + 1].isDigit()) {
                nums.add(line[idx] + goRight(line, idx))
            }
        }
    }
    return nums.map { it.replace("""\D""".toRegex(), "") }.filter { it.isNotBlank() }
}

fun goLeft(line: String, idx: Int): String {
    val l = line.substring(0, idx)
    return regexLeftNums.find(l)?.value ?: ""
}

val regexLeftNums = """\d+$""".toRegex()
val regexRightNums = """^\d+""".toRegex()
fun goRight(line: String, idx: Int): String {

    if (idx == line.lastIndex) {
        return ""
    }
    val s = line.substring(idx + 1)
    return regexRightNums.find(s)?.value ?: ""
}

private val checkValidRegexp = """\d|\.""".toRegex()

private fun List<String>.getNeighborLines(lineIndex: Int): List<String> {
    val start = max(lineIndex - 1, 0)
    val end = min(lineIndex + 2, this.lastIndex + 1)
    return this.subList(start, end)
}

private fun getAround(start: Int, end: Int, lines: List<String>): String {
    val s = max(start - 1, 0)
    val e = min(end + 1, lines[0].length)
    return lines.joinToString("") { it.substring(s, e) }.replace(checkValidRegexp, "").trim()
}

fun findNumbers(line: String, neighborLines: List<String>): List<Pair<Int, String>> {
    val res = mutableListOf<Pair<Int, String>>()
    var num = ""
    line.forEachIndexed { idx, ch ->
        if (ch.isDigit()) {
            num += ch
        }
        if (!ch.isDigit() || idx == line.lastIndex) {
            if (num.isNotEmpty()) {
                res.add(Pair(num.toInt(), getAround(idx - num.length, idx, neighborLines)))
            }
            num = ""
        }
    }
    return res
}
