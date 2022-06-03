import java.math.BigInteger
import java.nio.ByteOrder
import kotlin.Array
import kotlin.Byte
import kotlin.ByteArray
import kotlin.Char
import kotlin.Exception
import kotlin.ExperimentalUnsignedTypes
import kotlin.Int
import kotlin.IntArray
import kotlin.Long
import kotlin.OptIn
import kotlin.Short
import kotlin.String
import kotlin.Throws
import kotlin.UByte
import kotlin.UByteArray
import kotlin.toUByte


/**
 * Struct
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
 */
@OptIn(ExperimentalUnsignedTypes::class)
class Struct {
//    private fun packRaw_u16b(`val`: Int): ByteArray {
//        var `val` = `val`
//        var bx = ByteArray(2)
//        `val` = `val` and 0xffff //truncate
//        if (`val` >= 0) {
//            bx[0] = (`val` and 0xff).toByte()
//            bx[1] = (`val` shr 8 and 0xff).toByte()
//        }
//        if (byteOrder == BigEndian) {
//            bx = reverseBytes(bx)
//        }
//        return bx
//    }

//    private fun packRaw_32b(`val`: Int): ByteArray {
//        var bx = ByteArray(4)
//        if (`val` >= 0) {
//            bx[0] = (`val` and 0xff).toByte()
//            bx[1] = (`val` shr 8 and 0xff).toByte()
//            bx[2] = (`val` shr 16 and 0xff).toByte()
//            bx[3] = (`val` shr 24 and 0xff).toByte()
//        } else {
//            var v2 = Math.abs(`val`).toLong()
//            v2 = (v2 xor 0x7fffffff) + 1 // invert bits and add 1
//            v2 = v2 or (1 shl 31) // add the 32nd bit as negative bit
//            bx[0] = (v2 and 0xff).toByte()
//            bx[1] = (v2 shr 8 and 0xff).toByte()
//            bx[2] = (v2 shr 16 and 0xff).toByte()
//            bx[3] = (v2 shr 24 and 0xff).toByte()
//        }
//        if (byteOrder == BigEndian) {
//            bx = reverseBytes(bx)
//        }
//        return bx
//    }

//    private fun packRaw_u32b(`val`: Long): ByteArray {
//        var `val` = `val`
//        var bx = ByteArray(4)
//        `val` = `val` and -0x1
//        if (`val` >= 0) {
//            bx[0] = (`val` and 0xff).toByte()
//            bx[1] = (`val` shr 8 and 0xff).toByte()
//            bx[2] = (`val` shr 16 and 0xff).toByte()
//            bx[3] = (`val` shr 24 and 0xff).toByte()
//        }
//        if (byteOrder == BigEndian) {
//            bx = reverseBytes(bx)
//        }
//        return bx
//    }

//    fun pack_single_data(fmt: Char, `val`: Long): ByteArray? {
//        val bx: ByteArray?
//        bx = when (fmt) {
//            'h' -> {
//                val value = (`val` and 0xffff).toShort()
//                packRaw_16b(value)
//            }
//            'H' -> packRaw_u16b(`val`.toInt())
//            'i' -> {
//                val ival = (`val` and -0x1).toInt()
//                packRaw_32b(ival)
//            }
//            'I' -> packRaw_u32b(`val`)
//            else -> {
//                //do nothing
//                println("Invalid format specifier")
//                null
//            }
//        }
//        return bx
//    }

//    @Throws(Exception::class)
//    fun pack(fmt: String, `val`: Long): ByteArray? {
//        if (fmt.length > 2) {
//            throw Exception("Single values may not have multiple format specifiers")
//        }
//        var bx: ByteArray? = ByteArray(1)
//        for (i in 0 until fmt.length) {
//            val c = fmt[i]
//            if (i == 0 && (c == '>' || c == '<' || c == '@' || c == '!')) {
//                if (c == '>') byteOrder = BigEndian else if (c == '<') byteOrder =
//                    LittleEndian else if (c == '!') byteOrder = BigEndian else if (c == '@') byteOrder = nativeByteOrder
//            } else if (c != '>' && c != '<' && c != '@' && c != '!') {
//                bx = pack_single_data(c, `val`)
//                if (bx == null) throw Exception("Invalid character specifier")
//            }
//        }
//        return bx
//    }

//    @Throws(Exception::class)
//    fun pack(fmt: String, vals: LongArray): ByteArray {
//        val c0 = fmt[0]
//        var len: Int
//        len = if (c0 == '@' || c0 == '>' || c0 == '<' || c0 == '!') {
//            fmt.length - 1
//        } else {
//            fmt.length
//        }
//        if (len != vals.size) throw Exception("format length and values aren't equal")
//        len = lenEst(fmt)
//        var bxx = ByteArray(0)
//        var bx: ByteArray?
//        var temp: ByteArray
//        for (i in 0 until fmt.length) {
//            val c = fmt[i]
//            if (i == 0 && (c == '>' || c == '<' || c == '@' || c == '!')) {
//                if (c == '>') byteOrder = BigEndian else if (c == '<') byteOrder =
//                    LittleEndian else if (c == '!') byteOrder = BigEndian else if (c == '@') byteOrder = nativeByteOrder
//            } else if (c != '>' && c != '<' && c != '@' && c != '!') {
//                bx = if (c0 == '@' || c0 == '>' || c0 == '<' || c0 == '!') {
//                    pack(Character.toString(c), vals[i - 1])
//                } else {
//                    pack(Character.toString(c), vals[i])
//                }
//                temp = ByteArray(bxx.size + bx!!.size)
//                System.arraycopy(bxx, 0, temp, 0, bxx.size)
//                System.arraycopy(bx, 0, temp, bxx.size, bx.size)
//                bxx = Arrays.copyOf(temp, temp.size)
//            }
//        }
//        return bxx
//    }

    companion object {
        private const val BigEndian: Short = 0
        private const val LittleEndian: Short = 1
        private var byteOrder: Short = 0
        private fun reverseBytes(b: ByteArray): ByteArray {
            var tmp: Byte
            for (i in 0 until b.size / 2) {
                tmp = b[i]
                b[i] = b[b.size - i - 1]
                b[b.size - i - 1] = tmp
            }
            return b
        }

        private fun reverseBytes(b: UByteArray): UByteArray {
            var tmp: UByte
            for (i in 0 until b.size / 2) {
                tmp = b[i]
                b[i] = b[b.size - i - 1]
                b[b.size - i - 1] = tmp
            }
            return b
        }

        private fun reverseBytes(b: IntArray): IntArray {
            var tmp: Int
            for (i in 0 until b.size / 2) {
                tmp = b[i]
                b[i] = b[b.size - i - 1]
                b[b.size - i - 1] = tmp
            }
            return b
        }


//        private fun packRaw_16b(`val`: Short): ByteArray {
//            var bx = ByteArray(2)
//            if (`val` >= 0) {
//                bx[0] = (`val` and 0xff).toByte()
//                bx[1] = (`val`.toInt() shr 8 and 0xff).toByte()
//            } else {
//                var v2 = abs(`val`.toInt())
//                v2 = (v2 xor 0x7fff) + 1 // invert bits and add 1
//                v2 = v2 or (1 shl 15)
//                bx[0] = (v2 and 0xff).toByte()
//                bx[1] = (v2 shr 8 and 0xff).toByte()
//            }
//            if (byteOrder == BigEndian) {
//                bx = reverseBytes(bx)
//            }
//            return bx
//        }

        private fun unpackRaw_16b(`val`: UByteArray): Long {
            if (byteOrder == LittleEndian) reverseBytes(`val`)
            var x: Long
            x = (`val`[0].toInt() shl 8 or (`val`[1].toInt() and 0xff)).toLong()
            if (x ushr 15 and 1 == 1L) {
                x = (x xor 0x7fff and 0x7fff) + 1 //2's complement 16 bit
                x *= -1
            }
            return x
        }

        private fun unpackRaw_u16b(`val`: UByteArray): Long {
            if (byteOrder == LittleEndian) reverseBytes(`val`)
            val x = (`val`[0].toUByte().toInt() and 0xff shl 8 or (`val`[1].toUByte().toInt() and 0xff)).toLong()
            return x
        }

        private fun unpackRaw_32b(byteArray: UByteArray): BigInteger {
            if (byteOrder == LittleEndian) reverseBytes(byteArray)

//            val a = byteArray[0].toInt() and 0x00ff shl 24
//            val b = byteArray[1].toInt() and 0x00ff shl 16
//            val c = byteArray[2].toInt() and 0x00ff shl 8
//            val d = byteArray[3].toInt() and 0x00ff
//            var e = (a or b or c or d).toLong()
//            if (x ushr 31 and 1 == 1L) {
//                x = (x xor 0x7fffffff and 0x7fffffff) + 1 //2's complement 32 bit
//                x *= -1
//            }
            var x = BigInteger(byteArray.toByteArray())
            return x
        }

        private fun unpackRaw_u32b(uByteArray: UByteArray): BigInteger {
            if (byteOrder == LittleEndian) reverseBytes(uByteArray)
//            val a = uByteArray[0].toInt() and 0x00ff shl 24
//            val b = uByteArray[1].toInt() and 0x00ff shl 16
//            val c = uByteArray[2].toInt() and 0x00ff shl 8
//            val d = uByteArray[3].toInt() and 0x00ff
//            var f = (a or b or c or d).toLong()
//            val x = (uByteArray[0].toInt() and 0xff).toLong() shl 24 or ((uByteArray[1].toInt() and 0xff).toLong() shl 16) or ((uByteArray[2].toInt() and 0xff).toLong() shl 8) or (uByteArray[3].toInt() and 0x00ff).toLong()
            var x = BigInteger(uByteArray.toByteArray())
            return x
        }


        private fun toBitArray(uByteArray: UByteArray): IntArray {
            val returnable = IntArray(uByteArray.size * 8)

            uByteArray.forEachIndexed { i, uByte ->
                for(bitIndex in 7 downTo 0) {
                    val bit = (uByte.toInt() shr bitIndex) and 0x01
                    returnable[i * 8 + (7 - bitIndex)] = bit
                }
            }
            return returnable
        }

        private fun bitsToInt(input : IntArray) : Int {
            var int = 0
            input.forEachIndexed { i, v ->
                int = int or (v shl ((input.size-1) - i))
            }
            return int
        }


        private fun unpackRaw_64b(byteArray: UByteArray): BigInteger {
            if (byteOrder == LittleEndian) reverseBytes(byteArray)

//            val a = byteArray[0].toInt() and 0x00ff shl 56
//            val b = byteArray[1].toInt() and 0x00ff shl 48
//            val c = byteArray[2].toInt() and 0x00ff shl 40
//            val d = byteArray[3].toInt() and 0x00ff shl 32
//            val e = byteArray[4].toInt() and 0x00ff shl 24
//            val f = byteArray[5].toInt() and 0x00ff shl 16
//            val g = byteArray[6].toInt() and 0x00ff shl 8
//            val h = byteArray[7].toInt() and 0x00ff
            var x = BigInteger(byteArray.toByteArray())// (a or b or c or d or e or f or g or h)
            var reparse = x.toByteArray()
//            if (x ushr 63 and 1 == 1L) {
//                x = (x xor 0x7fffffffffffffff and 0x7fffffffffffffff) + 1 //2's complement 32 bit
//                x *= -1
//            }
            return x
        }

        private fun unpackRaw_u64b(byteArray: UByteArray): BigInteger {
            if (byteOrder == LittleEndian) reverseBytes(byteArray)

            val a = byteArray[0].toInt() and 0x00ff shl 56
            val b = byteArray[1].toInt() and 0x00ff shl 48
            val c = byteArray[2].toInt() and 0x00ff shl 40
            val d = byteArray[3].toInt() and 0x00ff shl 32
            val e = byteArray[4].toInt() and 0x00ff shl 24
            val f = byteArray[5].toInt() and 0x00ff shl 16
            val g = byteArray[6].toInt() and 0x00ff shl 8
            val h = byteArray[7].toInt() and 0x00ff
//            val x = (a or b or c or d or e or f or g or h).toLong()
            var x = BigInteger(byteArray.toByteArray())
//            val x = (`val`[0].toInt() and 0xff).toLong() shl 24 or ((`val`[1].toInt() and 0xff).toLong() shl 16) or ((`val`[2].toInt() and 0xff).toLong() shl 8) or (`val`[3].toInt() and 0xff).toLong()
            return x
        }

        private fun toByteArray(intArray: IntArray): ByteArray {
            val trueByteArray = ByteArray(intArray.size)
            intArray.forEachIndexed { i, x ->
                trueByteArray[i] = x.toByte()
            }
            return trueByteArray
        }

        @OptIn(ExperimentalUnsignedTypes::class)
        @Throws(Exception::class)
        fun unpack_single_data(fmt: Char, byteArray: UByteArray): String {
            var returnable = ""
            when (fmt) {
                'a' -> {
                    for(i in 0 until 64) {
                        returnable += byteArray[i].and(0xFF.toUByte()).toInt().toChar().toString()
                    }
                }
                'B' -> {
                    if (byteArray.size != 1) throw Exception("Byte length mismatch")
                    returnable = byteArray[0].toInt().toString()
                }
                'b', 'M' -> {
                    if (byteArray.size != 1) throw Exception("Byte length mismatch")
                    returnable = byteArray[0].toInt().toString()

                }
                'h' -> {
                    if (byteArray.size != 2) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_16b(byteArray).toString() + ""
                }
                'H' -> {
                    if (byteArray.size != 2) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_u16b(byteArray).toString() + ""
                }
                'i' -> {
                    if (byteArray.size != 4) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_32b(byteArray).toString() + ""
                }
                'I' -> {
                    if (byteArray.size != 4) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_u32b(byteArray).toString() + ""
                }
                'f' -> {
                    if (byteArray.size != 4) throw Exception("Byte length mismatch")
                    val a = unpackRaw_32b(byteArray).toInt()
                    val f = Float.fromBits(a)
                    returnable = f.toString()
                }
                'n' -> {
                    var s = ""
                    for (i in 0 until 4) {
                        val c = byteArray[i].and(0xFF.toUByte()).toInt().toChar()
                        s += c.toString()
                    }
                    returnable = s
                }
                'N' -> {
                    for(i in 0 until 16) {
                        returnable += byteArray[i].and(0xFF.toUByte()).toInt().toChar().toString()
                    }
                }
                'Z' -> {
                    for(i in 0 until 64) {
                        returnable += byteArray[i].and(0xFF.toUByte()).toInt().toChar().toString()
                    }
                }

                'c' -> {
                    if (byteArray.size != 2) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_16b(byteArray).toString()

                }
                'C' -> {
                    if (byteArray.size != 2) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_u16b(byteArray).toString()
                }
                'e' -> {
                    if (byteArray.size != 4) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_32b(byteArray).toString()
                }
                'E' -> {
                    if (byteArray.size != 4) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_u32b(byteArray).toString()
                }
                'L' -> {
                    if (byteArray.size != 4) throw Exception("Byte length mismatch")
                    val bigInt = unpackRaw_32b(byteArray)
                    returnable = bigInt.toString()
                }
                'd' -> {
                    if (byteArray.size != 8) throw Exception("Byte length mismatch")
                    val a = unpackRaw_64b(byteArray).toString()
                    val d = Double.fromBits(a.toLong())
                    returnable = d.toString()
                }
                'q' -> {
                    if (byteArray.size != 8) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_64b(byteArray).toString()
                }
                'Q' -> {
                    if (byteArray.size != 8) throw Exception("Byte length mismatch")
                    returnable = unpackRaw_u64b(byteArray).toString()
                }
                'x' -> {
                    if (byteArray.size != 1) throw Exception("Byte length mismatch")
                }
                else -> {
                }
            }
            return returnable
        }

//        private fun lenEst(fmt: String): Int {
//            var counter = 0
//            var x = '\u0000'
//            for (i in 0 until fmt.length) {
//                x = fmt[i]
//                if (x == 'a' || x == 'Z') counter += 64 else if (x == 'N') counter += 16 else if (x == 'q' || x == 'Q' || x == 'd') counter += 8 else if (x == 'i' || x == 'I' || x == 'l' || x == 'L' || x == 'f' || x == 'n') counter += 4 else if (x == 'h' || x == 'H' || x == 'e' || x == 'E') counter += 2 else if (x == 'b' || x == 'B' || x == 'c' || x == 'C' || x == '?' || x == 'M') counter++
//            }
//            return counter
//        }

        /**
         * Converts an Array of unsigned bytes from a dataflash log, to and array of strings
         * @param fmt a strings array which should be included for the message type in the dataflash log prior to
         * receiving a message of that type. See {@link DFFormat#}
         */
        @Throws(Exception::class)
        fun unpack(fmt: String, bytesVals: UByteArray): Array<String> {
            val x = ByteOrder.nativeOrder()
            byteOrder = if (x == ByteOrder.LITTLE_ENDIAN) LittleEndian else BigEndian

            val returnableAL = arrayListOf<String>()

            var pos = 0

//            fmt.forEach { fmtChar ->
            for(i in 0 until fmt.length) {
                val fmtChar = fmt[i]
                val len = when(fmtChar) {
                    'x', 'b', 'B', 'M' -> 1
                    'h', 'H', 'c', 'C' -> 2
                    'i', 'I', 'f', 'n', 'e', 'E', 'L' -> 4
                    'd', 'q', 'Q' -> 8
                    'N' -> 16
                    'a', 'Z' -> 64
                    else -> throw Exception("Format Exception")
                }
                returnableAL.add(unpack_single_data(fmtChar, bytesVals.copyOfRange(pos, pos+len)))
                pos += len
            }

            return returnableAL.toTypedArray()
        }
    }
}