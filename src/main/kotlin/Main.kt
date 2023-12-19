import kotlin.time.measureTimedValue

fun main() {
    val (value, timeTaken) = measureTimedValue {

        day19_1(lines = readData("day19.txt"))
//        day19_2(lines = readData("day19.txt"))
//        day19_1(lines = readData("day19.1.test.txt"))


    }
    println("\n------\nresult: $value (${timeTaken.inWholeMilliseconds} ms)\n------")
}

