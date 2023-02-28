const val charPrecision = 6
const val decPrecision = 2
const val marginRight = 4
const val width = charPrecision + marginRight + 1 + decPrecision + marginRight

class YAxis(private val chartData: ChartData) {

    fun priceToHeight(price: Double): Double {
        val height = chartData.height

        val minValue = chartData.visibleCandleSet.minPrice
        val maxValue = chartData.visibleCandleSet.maxPrice

        return (price - minValue) / (maxValue - minValue) * height
    }

    fun renderLine(y: Int): String {
        return when (y % 4) {
            0 -> renderTick(y)
            else -> renderEmpty()
        }
    }

    fun renderTick(y: Int): String {
        val minValue = chartData.visibleCandleSet.minPrice
        val maxValue = chartData.visibleCandleSet.maxPrice
        val height = chartData.height

        val price = minValue + (y * (maxValue - minValue) / height)
        val cellMinLength = charPrecision + decPrecision + 1

        return "${"%.2f".format(price).padStart(cellMinLength, ' ')} │┈${" ".repeat(marginRight)}"
    }

    fun renderEmpty(): String {
        val cell = " ".repeat(charPrecision + decPrecision + 2)
        val margin = " ".repeat(marginRight + 1)

        return "$cell|$margin"
    }
}