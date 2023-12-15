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

val Box.focalPower: Int get() = this.mapIndexed { idx, op -> (idx + 1) * op.focalLen }.sum()

fun Box.addLenses(op: Operation) {
    val idx = this.indexOfFirst { it.lensLabel == op.lensLabel }
    if (idx >= 0) {
        this.removeAt(idx)
        this.add(idx, op)
    } else this.add(op)
}

fun Box.removeLenses(op: Operation) {
    val idx = this.indexOfFirst { it.lensLabel == op.lensLabel }
    if (idx >= 0) this.removeAt(idx)
}

fun String.aocHash(): Int = this.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }
typealias Operation = String

val Operation.lensLabel: String get() = this.split("=", "-")[0]
val Operation.box: Int get() = lensLabel.aocHash()
val Operation.isAdd: Boolean get() = this.contains('=')
val Operation.focalLen: Int get() = if (isAdd) this.split("=", "-")[1].toInt() else 0
