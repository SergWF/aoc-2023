fun day7_1(lines: List<String>): Int = lines.map { it.split(" ") }.map { it.first() to it.last().toInt() }
    .sortedBy { (card, _) -> card.ratingBy() }
    .mapIndexed { idx, (card, bid) -> card to bid * (idx + 1) }
    .sumOf { (_, bid) -> bid }


fun day7_2(lines: List<String>): Int =
    lines.map { it.split(" ") }.map { it.first() to it.last().toInt() }
        .sortedBy { (card, _) -> card.ratingBy72() }
        .mapIndexed { idx, (card, bid) -> card to bid * (idx + 1) }
        .sumOf { (_, bid) -> bid }

fun String.ratingBy(): String = "${this.getType()} ${this.getStrengthBy(cards)}"
fun String.ratingBy72(): String = "${this.getType72()} ${this.getStrengthBy(cards.apply { this['J'] = '1' })}"

fun String.getType(): String = this.toList().groupingBy { it }.eachCount().values.sortedDescending().joinToString("")
fun String.getType72(): String =
    this.processJoker().toList().groupingBy { it }.eachCount().values.sortedDescending().joinToString("")

fun String.getStrengthBy(cards: Map<Char, Char>): String = this.map { it.valueBy(cards) }.joinToString("")
fun String.processJoker(): String = if (this.contains('J') && this != "JJJJJ") {
    val charToExtend = this.toList().filter { it != 'J' }.groupingBy { it }.eachCount().maxBy { it.value }.key
    this.replace('J', charToExtend)
} else {
    this
}

private val cards = mutableMapOf(
    '2' to '2',
    '3' to '3',
    '4' to '4',
    '5' to '5',
    '6' to '6',
    '7' to '7',
    '8' to '8',
    '9' to '9',
    'T' to 'a',
    'J' to 'b',
    'Q' to 'c',
    'K' to 'd',
    'A' to 'e'
)


private fun Char.valueBy(cards: Map<Char, Char>): Char = cards[this] ?: ' '