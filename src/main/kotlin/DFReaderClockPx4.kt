/**
 * DFReaderClockPx4
 * Copyright (C) 2021 Hitec Commercial Solutions
 * @author Stephen Woerner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * This software is based on:
 * APM DataFlash log file reader
 * Copyright Andrew Tridgell 2011
 *
 * Released under GNU GPL version 3 or later
 * Partly based on SDLog2Parser by Anton Babushkin
 */

/**
 * DFReaderClockPx4 - a format where a starting time is explicitly given in a message
 */
class DFReaderClockPx4 : DFReaderClock() {

    var px4Timebase : Int = 0

    /**
     * work out time basis for the log - PX4 native
     */
    fun findTimeBase(gps : DFMessage) {
        val t = gps.GPSTime!! * 1.0e-6
        timebase = t - px4Timebase
    }

    fun setPx4Timebase(time_msg: DFMessage) {
        px4Timebase = (time_msg.StartTime!! * 1.0e-6).toInt()
    }

    override fun setMessageTimestamp(m: DFMessage) {
        m.timestamp = (timebase + px4Timebase).toLong()
    }

    override fun messageArrived(m : DFMessage) {
        val type = m.getType()
        if (type == "TIME" && "StartTime" in m.fieldnames) {
            setPx4Timebase(m)
        }
    }
}
