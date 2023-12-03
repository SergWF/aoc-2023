fun day2_1(lines: List<String>) =
    lines.map {
        val r = it.split("[:,;]".toRegex())
        val idx = r[0].filter { it.isDigit() }.toInt()
        val cubes = r.subList(1, r.size)
        idx to cubes.all { checkLimit(it) }
    }.filter { it.second }.sumOf { it.first }

fun day2_2(lines: List<String>) =
    lines.map { it.parseGame() }
        .map {it.getMaxRound().power }
        .sum()

private fun checkLimit(cube: String): Boolean {
    val c = cube.trim().split(" ")
    val cnt = c[0].toInt()
    val color = Limits.valueOf(c[1])
    return cnt <= color.limit
}

enum class Limits(val limit: Int) {
    red(12),
    green(13),
    blue(14)
}

data class Game(val idx: Int, val rounds: List<Round>) {
    fun getMaxRound(): Round = rounds.fold(Round(0, 0, 0)) {acc, round ->
        Round(
            red = maxOf(acc.red, round.red),
            blue = maxOf(acc.blue, round.blue),
            green = maxOf(acc.green, round.green)
        )
    }
}
data class Round(val red: Int, val green: Int, val blue: Int) {
    val power: Int = red * green * blue
}

private fun String.parseGame(): Game {
    val s = this.split(":")
    return Game(
        idx = s[0].filter { it.isDigit() }.toInt(),
        rounds = s[1].split(";").map { it.parseRound() }
    )
}


private fun String.parseRound(): Round {
    val r: Map<String, Int> = this.split(",").map {
        val s = it.trim().split(" ")
        s[1] to s[0].toInt()
    }.toMap()
    return Round(red = r["red"] ?: 0, blue = r["blue"] ?: 0, green = r["green"] ?: 0)
}


