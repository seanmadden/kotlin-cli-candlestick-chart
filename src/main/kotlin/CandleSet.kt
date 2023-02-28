data class CandleSet(var candles: List<Candle>) {
    val variation = ((candles.last().close - candles.first().open) / candles.first().open) * 100.0
    val average = candles.sumOf { it.close } / candles.size
    val lastPrice = candles.last().close
    val cumulativeVolume = candles.sumOf { it.volume ?: 0 }

    val maxPrice = candles.maxOf { it.high }
    val minPrice = candles.minOf { it.low }

    val maxVolume = candles.maxOf { it.volume ?: 0 }
    val minVolume = candles.minOf { it.volume ?: 0 }
}