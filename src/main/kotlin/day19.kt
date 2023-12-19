import kotlin.math.abs

fun day19_1(lines: List<String>): Int {
    val (rules, parts) = lines.splitByEmptyLine()
    return parts.processItems(rules.parseRules()).sumOf { it.rating }
}

fun day19_2(lines: List<String>): Int =
    lines.splitByEmptyLine().first()

        .count()

private data class Selector(val param: Char, val limit: Int, val dest: String)
private data class Rule(val name: String, val selectors: List<Selector>)
private typealias Part = Map<Char, Int>

private val terminalRules = mapOf(
    "A" to Rule("A", emptyList()),
    "R" to Rule("R", emptyList())
)

private fun List<String>.parseRules(): List<Rule> = this.map { it.toRule() }
private fun List<String>.processItems(rules: List<Rule>): List<Part> = this.mapNotNull { it.toPart().processRules(rules) }


private val Part.rating: Int get() = this.values.sum()

private fun Part.processRules(rules: List<Rule>): Part? {
    val r = this.processRule(rules.first { it.name == "in" }, rules)
    return if (r.name == "A") {
        this
    } else {
        null
    }
}

private fun Part.processRule(rule: Rule, rules: List<Rule>): Rule {
    val selector = rule.selectors.find { sel -> sel.isOk(this) } ?: rule.selectors.last()
    return terminalRules[selector.dest] ?: this.processRule(rules.first { it.name == selector.dest }, rules)
}

private fun Selector.isOk(part: Part): Boolean {
    if(this.param == 'U') return true
    val p = this.param
    val v = part[this.param]
    return if (this.limit > 0) {
        v!! > abs(this.limit)
    } else {
        v!! < abs(this.limit)
    }
}

private fun String.toPart(): Part =
    this.replace("{", "").replace("}", "")
        .split(",").associate {
            it.split("=")
                .let { (k, v) ->
                    k.first() to v.toInt()
                }
        }

private fun String.toRule(): Rule = this.split("{", "}").let { Rule(it[0], it[1].toSelectors()) }

private fun String.toSelectors(): List<Selector> =
    this.split(",").map { s ->
        s.split(":")
            .let { selStr ->
                if (selStr.size == 2) {
                    Selector(selStr.first().first(), selStr.first().toSelectorVal(), selStr.last())
                } else {
                    Selector('U', 0, selStr.last())
                }
            }
    }


private fun String.toSelectorVal(): Int = this.substring(2, this.length).toInt() * if (this[1] == '>') 1 else -1
