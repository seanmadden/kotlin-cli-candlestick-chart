import java.awt.Color
import kotlin.math.ceil

class VolumePane(
    val chartData: ChartData,
    var height: Int,
    val bearishColor: Color = Color(52, 208, 88),
    val bullishColor: Color = Color(234, 74, 90),
    var unicodeFill: Char = '┃'
) {
    var enabled: Boolean

    init {
        val hasVolume = chartData.visibleCandleSet.candles.any { (it.volume ?: 0) > 0 }
        enabled = hasVolume
    }

    fun colorize(candleType: CandleType, string: String): String {
        return when (candleType) {
            CandleType.Bullish ->"$green$string$reset"
            CandleType.Bearish -> "$red$string$reset"
        }
    }

    fun render(candle: Candle, y: Int): String {
        val maxVolume = chartData.visibleCandleSet.maxVolume
        val volume = candle.volume ?: 0

        val volumePercentOfMax = volume.toDouble() / maxVolume.toDouble()
        val ratio = volumePercentOfMax * height

        if (y < ceil(ratio)) {
            return colorize(candle.getType(), unicodeFill.toString())
        }

        if (y == 1 && unicodeFill == '┃') {
            return colorize(candle.getType(), "╻")
        }

        return " "
    }
}