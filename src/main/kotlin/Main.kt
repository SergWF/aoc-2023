import kotlin.time.measureTimedValue

fun main() {
    val (value, timeTaken) = measureTimedValue {

//        day16_1(lines = readData("day16.txt"))
        day16_2(lines = readData("day16.txt"))
//        day16_2(lines = readData("day16.1.test.txt"))


    }
    println("\n------\nresult: $value (${timeTaken.inWholeMilliseconds} ms)\n------")
}

