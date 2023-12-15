import kotlin.time.measureTimedValue

fun main() {
    val (value, timeTaken) = measureTimedValue {

//        day15_1(lines = readData("day15.txt"))
//        day15_2(lines = readData("day15.txt"))
        day15_1(lines = readData("day15.1.test.txt"))


    }
    println("\n------\nresult: $value (${timeTaken.inWholeMilliseconds} ms)\n------")
}

