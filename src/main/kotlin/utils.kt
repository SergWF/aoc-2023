import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readData(taskFile: String): List<String> = Path("src/main/resources/$taskFile").readLines()


