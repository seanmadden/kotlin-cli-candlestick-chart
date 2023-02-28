import java.awt.Color
import kotlin.math.ceil
import kotlin.math.floor

const val marginTop = 3

val red = "\u001b[91m"
val green = "\u001b[92m"
val reset = "\u001b[0m"

class ChartRenderer(
    val bearishColor: Color = Color(52, 208, 88),
    val bullishColor: Color = Color(234, 74, 90),
) {
    private val UNICODE_VOID = ' '
    private val UNICODE_BODY = '┃'
    private val UNICODE_HALF_BODY_BOTTOM = '╻'
    private val UNICODE_HALF_BODY_TOP = '╹'
    private val UNICODE_WICK = '│'
    private val UNICODE_TOP = '╽'
    private val UNICODE_BOTTOM = '╿'
    private val UNICODE_UPPER_WICK = '╷'
    private val UNICODE_LOWER_WICK = '╵'

    fun colorize(candleType: CandleType, string: String): String {
        return when (candleType) {
            CandleType.Bullish -> "$green$string$reset"
            CandleType.Bearish -> "$red$string$reset"
        }
    }

    fun renderCandle(candle: Candle, y: Int, yAxis: YAxis): String {
        val heightUnit = y
        val highY = yAxis.priceToHeight(candle.high)
        val lowY = yAxis.priceToHeight(candle.low)
        val maxY = yAxis.priceToHeight(candle.open.coerceAtLeast(candle.close))
        val minY = yAxis.priceToHeight(candle.close.coerceAtMost(candle.open))

        var output = UNICODE_VOID
        if (ceil(highY) >= heightUnit && heightUnit >= floor(maxY)) {
            if (maxY - heightUnit > 0.75) {
                output = UNICODE_BODY
            } else if (maxY - heightUnit > 0.25) {
                if (highY - heightUnit > 0.75) {
                    output = UNICODE_TOP
                } else {
                    output = UNICODE_HALF_BODY_BOTTOM
                }
            } else if (highY - heightUnit > 0.75) {
                output = UNICODE_WICK
            } else if (highY - heightUnit > 0.25) {
                output = UNICODE_UPPER_WICK
            }
        } else if (floor(maxY) >= heightUnit && heightUnit >= ceil(minY)) {
            output = UNICODE_BODY
        } else if (ceil(minY) >= heightUnit && heightUnit >= floor(lowY)) {
            if (minY - heightUnit < 0.25) {
                output = UNICODE_BODY
            } else if (minY - heightUnit < 0.75) {
                if (lowY - heightUnit < 0.25) {
                    output = UNICODE_BOTTOM
                } else {
                    output = UNICODE_HALF_BODY_TOP
                }
            } else if (lowY - heightUnit < 0.25) {
                output = UNICODE_WICK
            } else if (lowY - heightUnit < 0.75) {
                output = UNICODE_LOWER_WICK
            }
        }

        return colorize(candle.getType(), output.toString())
    }

    fun renderToString(chart: Chart): String {

        val output = StringBuilder()

        val chartData = chart.chartData
        chartData.computeHeight(chart.volumePane)

        for (y in (1..chartData.height).reversed()) {
            output.appendLine()
            output.append(chart.yAxis.renderLine(y))

            for (candle in chartData.visibleCandleSet.candles) {
                output.append(renderCandle(candle, y, chart.yAxis))
            }
        }

        if (chart.volumePane.enabled) {
            for (y in (1..chart.volumePane.height + 1).reversed()) {
                output.appendLine()
                output.append(chart.yAxis.renderEmpty())

                for (candle in chartData.visibleCandleSet.candles) {
                    output.append(chart.volumePane.render(candle, y))
                }
            }
        }

        output.append(chart.infoBar.render())

        return output.appendLine().toString()
    }

    fun render(chart: Chart) {
        println(renderToString(chart))
    }

}