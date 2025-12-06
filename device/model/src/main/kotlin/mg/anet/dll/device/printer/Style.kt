package mg.anet.dll.device.printer

data class Style(
    val size: Size = Size.Small,
    val alignment: Alignment = Alignment.Left,
    val bold: Boolean = false,
    val invert: Boolean = false,
) {
    enum class Size {
        Small,
        Medium,
        Normal,
        Big
    }

    enum class Alignment {
        Left,
        Right,
        Center
    }
}