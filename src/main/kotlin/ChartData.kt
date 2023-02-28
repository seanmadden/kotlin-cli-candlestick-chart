class ChartData(
    candles: List<Candle>,
    val terminalSize: Pair<Int, Int>
) {
    val mainCandleSet: CandleSet
    var visibleCandleSet: CandleSet
    var height: Int

    init {
        mainCandleSet = CandleSet(candles)
        visibleCandleSet = CandleSet(computeVisibleCandles())

        height = 50
    }

    fun computeHeight(volumePane: VolumePane) {
        val volumePaneHeight = if (volumePane.enabled) volumePane.height else 0

        height = terminalSize.first - marginTop - volumePaneHeight
    }

    fun computeVisibleCandles(): List<Candle> {
        val termWidth = terminalSize.second
        val nbCandles = mainCandleSet.candles.size

        val nbVisibleCandles = termWidth - width

        return mainCandleSet.candles
            .takeLast(nbVisibleCandles)
    }
}