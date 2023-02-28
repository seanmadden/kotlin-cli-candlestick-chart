class InfoBar(var name: String, private val chartData: ChartData) {
    val height = 2

    fun render(): String {
        val mainSet = chartData.mainCandleSet
        val visibleSet = chartData.visibleCandleSet

        val candles = visibleSet.candles
        val output = StringBuilder()

        output.appendLine()
        output.append("─".repeat(candles.size + width))
        output.appendLine()


        val avgFormat = "${mainSet.average}"

        val variationOutput = if (mainSet.variation > 0.0) {
            Pair("↖", "green")
        } else {
            Pair("↙", "red")
        }

        output.append(
            ("| Price: ${mainSet.lastPrice} " +
                    "| Highest: ${mainSet.maxPrice} " +
                    "| Lowest ${mainSet.minPrice} " +
                    "| Var: ${variationOutput.first} " +
                    "| Avg: $avgFormat " +
                    "| Cum Vol: ${mainSet.cumulativeVolume}").padStart(width)
        )

        return output.toString()
    }
}