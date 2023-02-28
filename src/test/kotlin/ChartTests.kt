import org.junit.jupiter.api.Test
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChartTests {

    @Test
    fun testSPXChart() {
        val candles =
            with(File("src/test/resources/SPX-2022-07-01.csv").readLines()) {
                // 2022-07-01 09:30:00,3781.00000,3785.32000,3778.63000,3784.85000,0
                this.map {
                    val split = it.split(",")

                    Candle(
                        open = split[1].toDouble(),
                        high = split[2].toDouble(),
                        low = split[3].toDouble(),
                        close = split[4].toDouble(),
                        volume = split[5].toLong(),
                        timestamp = LocalDateTime.parse(
                            split[0],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        ).atZone(ZoneId.of("America/New_York")).toEpochSecond()
                    )
                }
            }

        val chart = Chart(candles, terminalSize = Pair(46, 204))
        chart.draw()

    }

    @Test
    fun testMESChart() {
        val candles =
            with(File("src/test/resources/MES-2023-02-24.csv").readLines()) {
                // 2022-07-01 09:30:00,3781.00000,3785.32000,3778.63000,3784.85000,0
                this.map {
                    val split = it.split(",")

                    Candle(
                        open = split[1].toDouble(),
                        high = split[2].toDouble(),
                        low = split[3].toDouble(),
                        close = split[4].toDouble(),
                        volume = split[5].toLong(),
                        timestamp = Instant.parse(split[0]).epochSecond
                    )
                }
            }

        val chart = Chart(candles, terminalSize = Pair(46, 204))
        chart.draw()

    }
}