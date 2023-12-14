import kotlin.time.measureTimedValue

fun main() {
    val (value, timeTaken) = measureTimedValue {

//        day14_1(lines = readData("day14.txt"))
        day14_2(lines = readData("day14.txt"))
//        day14_2(lines = readData("day14.1.test.txt"))


    }
    println("\n------\nresult: $value (${timeTaken.inWholeMilliseconds} ms)\n------")
}

