fun day13_1(lines: List<String>): Int = lines.splitByEmptyLine().sumOf { printData(it); it.mirrorValue() ?: 0 }

private fun List<String>.mirrorValue(): Int? {
    val res = this.hor() ?: this.vert()
    println("res=$res")
    return res
}

private fun List<String>.hor(): Int? = this.map { it.toBinNum() }.mirrorIndex()?.let { it * 100 }
private fun List<String>.vert(): Int? = this.pivot().map { it.toBinNum() }.mirrorIndex()
private fun String.toBinNum(): Int = this.replace('.', '0').replace('#', '1').toInt(2)

private fun List<Int>.mirrorIndex(): Int? {
    if (this.size <= 1) return null
    val mid = this.size / 2
    (1..mid).reversed().forEach { idx ->
        if (this.mirroredOnIndex(idx)) {
            return idx
        }
        if (this.reversed().mirroredOnIndex(idx)) {
            return mid + (mid - idx) + 1
        }
    }
    return null
}

private fun List<Int>.mirroredOnIndex(index: Int): Boolean {
    val left = this.subList(0, index)
    val rest = this.subList(index, index * 2)
    return left == rest.reversed()
}

