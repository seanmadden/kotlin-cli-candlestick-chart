class Candle(
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long?,
    val timestamp: Long?
) {
    fun getType(): CandleType {
        return if (open < close) {
            CandleType.Bullish
        } else {
            CandleType.Bearish
        }
    }
}

enum class CandleType {
    Bearish,
    Bullish,
}

class Chart(val candles: List<Candle>, terminalSize: Pair<Int, Int> = Pair(100, 100)) {

    private val renderer = ChartRenderer()
    val chartData = ChartData(candles, terminalSize = terminalSize)
    val yAxis = YAxis(chartData)
    val infoBar = InfoBar("/MES", chartData)
    val volumePane = VolumePane(chartData, chartData.terminalSize.first / 6)

    init {
        chartData.computeHeight(volumePane)
    }

    fun draw() {
        renderer.render(this)
    }

    fun renderToString(): String {
        return renderer.renderToString(this)
    }

    fun setName(name: String) {
        infoBar.name = name
    }

    fun setBullColor() {
        TODO()
    }

    fun setBearColor() {
        TODO()
    }

    fun setVolBullColor() {
        TODO()
    }

    fun setVolBearColor() {
        TODO()
    }

    fun setVolumePaneEnabled(enabled: Boolean) {
        volumePane.enabled = enabled
    }

    fun setVolumePaneUnicodeFull(fill: Char) {
        volumePane.unicodeFill = fill
    }

    fun setVolumePaneHeight(height: Int) {
        volumePane.height = height
    }

}