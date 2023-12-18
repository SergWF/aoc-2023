import kotlin.time.measureTimedValue

fun main() {
    val (value, timeTaken) = measureTimedValue {

        day18_1(lines = readData("day18.txt"))
//        day18_2(lines = readData("day18.txt"))
//        day18_1(lines = readData("day18.1.test.txt"))


    }
    println("\n------\nresult: $value (${timeTaken.inWholeMilliseconds} ms)\n------")
}

