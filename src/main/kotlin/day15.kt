fun day15_1(lines: List<String>): Int = lines.map { it.split(",") }.flatten().sumOf { it.aocHash() }

fun day15_2(lines: List<String>): Int {
    val boxes = (0..255).map { mutableListOf<String>() }
    lines.map { it.split(",") }.flatten()
        .forEach { op: Operation ->
            if (op.isAdd) boxes[op.box].addLenses(op) else boxes[op.box].removeLenses(op)
        }

    return boxes.mapIndexed { idx, box -> (idx + 1) * box.focalPower }.sum()
}

typealias Box = MutableList<Operation>

private val Box.focalPower: Int get() = this.mapIndexed { idx, op -> (idx + 1) * op.focalLen }.sum()

private fun Box.addLenses(op: Operation) {
    val idx = this.indexOfFirst { it.lensLabel == op.lensLabel }
    if (idx >= 0) {
        this.removeAt(idx)
        this.add(idx, op)
    } else this.add(op)
}

private fun Box.removeLenses(op: Operation) {
    val idx = this.indexOfFirst { it.lensLabel == op.lensLabel }
    if (idx >= 0) this.removeAt(idx)
}

private fun String.aocHash(): Int = this.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }
typealias Operation = String

private val Operation.lensLabel: String get() = this.split("=", "-")[0]
private val Operation.box: Int get() = lensLabel.aocHash()
private val Operation.isAdd: Boolean get() = this.contains('=')
private val Operation.focalLen: Int get() = if (isAdd) this.split("=", "-")[1].toInt() else 0
