import java.awt.BorderLayout
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.FileNotFoundException
import javax.swing.*
import kotlin.math.min


object Main {


    @JvmStatic
    fun main(args: Array<String>) {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            } catch (ex: ClassNotFoundException) {
            } catch (ex: InstantiationException) {
            } catch (ex: IllegalAccessException) {
            } catch (ex: UnsupportedLookAndFeelException) {
            }
            val label = JLabel("Loading log file...", JLabel.CENTER)
            Thread {
                JOptionPane.showMessageDialog(null, label,"Log analyzer", JOptionPane.PLAIN_MESSAGE)
            }.start()
            val dfParser = DataFlashParser("log.bin") { pct: Int -> label.text = "Loading log file: $pct%" }
            JOptionPane.getRootFrame().dispose()
            // mah
            var mah = 0.0
            with(dfParser.getFieldLists(hashSetOf("CurrTot"))) {
                mah = (get(keys.first())?.last()?.second as String).toDouble()
            }
            // wh
            var wh = 0.0
            with(dfParser.getFieldLists(hashSetOf("EnrgTot"))) {
                wh = (get(keys.first())?.last()?.second as String).toDouble()
            }
            // speed
            val speeds = arrayListOf<Double>()
            dfParser.getFieldLists(hashSetOf("Spd")).firstNotNullOf {
                it.value.forEach { spdData ->
                    speeds.add((spdData.second as String).toDouble())
                }
            }
            val avgSpeed = speeds.average()
            val maxSpeed = speeds.maxOrNull()
            // alt
            val alts = arrayListOf<Double>()
            dfParser.getFieldLists(hashSetOf("RelHomeAlt")).firstNotNullOf {
                it.value.forEach { altData ->
                    alts.add((altData.second as String).toDouble())
                }
            }
            val avgAlt = alts.average()
            val maxAlt = alts.maxOrNull()
            // distance
            val lons = mutableMapOf<Long, Double>()
            dfParser.getFieldLists(hashSetOf("Lng")).firstNotNullOf {
                it.value.forEach { lonData ->
                    lons[lonData.first] = lonData.second as Double
                }
            }
            val lats = mutableMapOf<Long, Double>()
            dfParser.getFieldLists(hashSetOf("Lat")).firstNotNullOf {
                it.value.forEach { latData ->
                    lats[latData.first] = latData.second as Double
                }
            }
            val cleanLats = lats.toSortedMap().values.toList()
            val cleanLons = lons.toSortedMap().values.toList()
            val minSize = min(cleanLats.size, cleanLons.size)
            var distance = 0.0
            var maxDistance = 0.0
            for (i in 0 until minSize - 1) {
                val lat1 = cleanLats[i]
                val lon1 = cleanLons[i]
                val lat2 = cleanLats[i + 1]
                val lon2 = cleanLons[i + 1]
                maxDistance = maxDistance.coerceAtLeast(DistanceUtil.getDistance(cleanLats[0], lat1, cleanLons[0], lon1))
                distance += DistanceUtil.getDistance(lat1, lat2, lon1, lon2)
            }
            distance /= 1000
            maxDistance /= 1000
            val whKm = wh / distance
            val mahKm = mah / distance

            val builder = StringBuilder()
            builder.appendLine("\r----------------Stats----------------")
            builder.appendLine("Energy: %.3f mah | %.3f wh".format(mah, wh))
            builder.appendLine("Speed: %.3f avg | %.3f max (m/s)".format(avgSpeed, maxSpeed))
            builder.appendLine("Altitude: %.3f avg | %.3f max (m)".format(avgAlt, maxAlt))
            builder.appendLine("Distance: %.3f total | %.3f max (km)".format(distance, maxDistance))
            builder.appendLine("\n-------------Efficiency-------------")
            builder.appendLine("%.3f mah/km | %.3f wh/km".format(mahKm, whKm))

            val gui = JPanel(BorderLayout())
            JOptionPane.showMessageDialog(
                null,
                builder.toString(),
                "Log analysis",
                JOptionPane.INFORMATION_MESSAGE
            )
        } catch (e: FileNotFoundException) {
            Thread.sleep(1000)
            JOptionPane.getRootFrame().dispose();
            JOptionPane.showMessageDialog(
                null,
                "Place the log in the same folder and name it log.bin",
                "Error",
                1
            )
        }
    }
}