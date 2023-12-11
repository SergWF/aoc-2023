import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow

val numberRegex = """\d+""".toRegex()

fun String.extractNumbers(): List<Long> = numberRegex.findAll(this).map(MatchResult::value).map { it.toLong() }.toList()
fun readData(taskFile: String): List<String> = Path("src/main/resources/$taskFile").readLines()


fun String.toIntList(): List<Int> = this.trim().split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() }
fun Int.pow(x: Int): Int = this.toFloat().pow(x.toFloat()).toInt()

fun <T> List<T>.getFirstIndexOf(start: Int = 0, predicate: (T) -> Boolean): Int = withIndex()
    .filter { predicate(it.value) && it.index > start }
    .map { it.index }
    .firstOrNull()
    ?: this.size

fun String.containsOnly(char: Char) = this.all { it == char }