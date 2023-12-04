fun day4_1(lines: List<String>) =
    lines.sumOf {
        val s = it.split(':', '|')
        val won = s[1].toIntList().toSet()
        val nums = s[2].toIntList().toSet()
        2.pow(won.intersect(nums).size - 1)
    }
//13768818
fun day4_2(lines: List<String>): Int {
    val cards: List<Card> = lines.map { line -> line.toCard() }
    cards.forEach { card ->
        cards.filter { it.id > card.id && it.id <= card.id + card.wins }
            .forEach { it.cnt += card.cnt }
    }
    return cards.sumOf { it.cnt }
}

private val numsRegex = """\d+""".toRegex()
private fun String.toCard(): Card {
    val s = this.split(':', '|')
    return Card(
        id = numsRegex.find(s[0])!!.value.toInt(),
        won = s[1].toIntList().toSet(),
        nums = s[2].toIntList().toSet()
    )
}

data class Card(
    val id: Int,
    val won: Set<Int>,
    val nums: Set<Int>,
    var cnt: Int = 1
) {
    val wins: Int = won.intersect(nums).size
}