import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow

fun readData(taskFile: String): List<String> = Path("src/main/resources/$taskFile").readLines()


fun String.toIntList(): List<Int> = this.trim().split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() }
fun Int.pow(x: Int): Int = this.toFloat().pow(x.toFloat()).toInt()