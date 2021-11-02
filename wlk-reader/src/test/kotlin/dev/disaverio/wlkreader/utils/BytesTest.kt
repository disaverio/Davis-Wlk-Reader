package dev.disaverio.wlkreader.utils

import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals


// Int - VALUES TO TEST ---------------------------------------------------------------------------------------

// bin      // hex // oct // sDec // uDec
// 01001010 // 4A  // 112 // +074 // 074
// 00000010 // 02  // 002 // +002 // 002
// 01100100 // 64  // 144 // +100 // 100
// 01110101 // 75  // 165 // +117 // 117
// 11001010 // CA  // 312 // -054 // 202
// 10000010 // 82  // 202 // -126 // 130
// 11100100 // E4  // 344 // -028 // 228
// 11110101 // F5  // 365 // -011 // 245
// 11111111 // FF  // 377 // -001 // 255

// 4 byte                              // hex         // sLE         // uLE        // sBE         // uBE
// 01001010 00000010 01100100 01110101 // 4A 02 64 75 // +1969488458 // 1969488458 // +1241670773 // 1241670773
// 01001010 00000010 01100100 11110101 // 4A 02 64 F5 // -0177995190 // 4116972106 // +1241670901 // 1241670901
// 01001010 00000010 11100100 01110101 // 4A 02 E4 75 // +1977877066 // 1977877066 // +1241703541 // 1241703541
// 01001010 10000010 01100100 01110101 // 4A 82 64 75 // +1969521226 // 1969521226 // +1250059381 // 1250059381
// 11001010 00000010 01100100 01110101 // CA 02 64 75 // +1969488586 // 1969488586 // -0905812875 // 3389154421
// 01001010 00000010 11100100 11110101 // 4A 02 E4 F5 // -0169606582 // 4125360714 // +1241703669 // 1241703669
// 01001010 10000010 01100100 11110101 // 4A 82 64 F5 // -0177962422 // 4117004874 // +1250059509 // 1250059509
// 11001010 00000010 01100100 11110101 // CA 02 64 F5 // -0177995062 // 4116972234 // -0905812747 // 3389154549
// 01001010 10000010 11100100 01110101 // 4A 82 E4 75 // +1977909834 // 1977909834 // +1250092149 // 1250092149
// 11001010 00000010 11100100 01110101 // CA 02 E4 75 // +1977877194 // 1977877194 // -0905780107 // 3389187189
// 11001010 10000010 01100100 01110101 // CA 82 64 75 // +1969521354 // 1969521354 // -0897424267 // 3397543029
// 01001010 10000010 11100100 11110101 // 4A 82 E4 F5 // -0169573814 // 4125393482 // +1250092277 // 1250092277
// 11001010 00000010 11100100 11110101 // CA 02 E4 F5 // -0169606454 // 4125360842 // -0905779979 // 3389187317
// 11001010 10000010 01100100 11110101 // CA 82 64 F5 // -0177962294 // 4117005002 // -0897424139 // 3397543157
// 11001010 10000010 11100100 01110101 // CA 82 E4 75 // +1977909962 // 1977909962 // -0897391499 // 3397575797
// 11001010 10000010 11100100 11110101 // CA 82 E4 F5 // -0169573686 // 4125393610 // -0897391371 // 3397575925
// 11111111 11111111 11111111 11111111 // FF FF FF FF // -0000000001 // 4294967295 // -0000000001 // 4294967295


// Short - VALUES TO TEST -------------------------------------------------------------------------------------

// bin      // hex // oct // sDec // uDec
// 00100010 // 22  // 042 // +034 // 034
// 00000101 // 05  // 005 // +005 // 005
// 10100010 // A2  // 242 // -094 // 162
// 10000101 // 85  // 205 // -123 // 133
// 11111111 // FF  // 377 // -001 // 255

// 2 byte            // hex   // sLE    // uLE   // sBE    // uBE
// 00100010 00000101 // 22 05 // +01314 // 01314 // +08709 // 08709
// 00100010 10000101 // 22 85 // -31454 // 34082 // +08837 // 08837
// 10100010 00000101 // A2 05 // +01442 // 01442 // -24059 // 41477
// 10100010 10000101 // A2 85 // -31326 // 34210 // -23931 // 41605
// 11111111 11111111 // FF FF // -00001 // 65535 // -00001 // 65535


class BytesTest {

    @Nested
    inner class ArrayConversion {

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1969488458",
            "01001010, 00000010, 01100100, 11110101, -177995190",
            "01001010, 00000010, 11100100, 01110101, 1977877066",
            "01001010, 10000010, 01100100, 01110101, 1969521226",
            "11001010, 00000010, 01100100, 01110101, 1969488586",
            "01001010, 00000010, 11100100, 11110101, -169606582",
            "01001010, 10000010, 01100100, 11110101, -177962422",
            "11001010, 00000010, 01100100, 11110101, -177995062",
            "01001010, 10000010, 11100100, 01110101, 1977909834",
            "11001010, 00000010, 11100100, 01110101, 1977877194",
            "11001010, 10000010, 01100100, 01110101, 1969521354",
            "01001010, 10000010, 11100100, 11110101, -169573814",
            "11001010, 00000010, 11100100, 11110101, -169606454",
            "11001010, 10000010, 01100100, 11110101, -177962294",
            "11001010, 10000010, 11100100, 01110101, 1977909962",
            "11001010, 10000010, 11100100, 11110101, -169573686",
            "11111111, 11111111, 11111111, 11111111, -1"
        )
        fun `'ByteArray#toIntLittleEndian' should correctly convert from Little Endian ByteArray to Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: Int) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2)).toByteArray()
            val result = input.toIntLittleEndian()

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1241670773",
            "01001010, 00000010, 01100100, 11110101, 1241670901",
            "01001010, 00000010, 11100100, 01110101, 1241703541",
            "01001010, 10000010, 01100100, 01110101, 1250059381",
            "11001010, 00000010, 01100100, 01110101, -905812875",
            "01001010, 00000010, 11100100, 11110101, 1241703669",
            "01001010, 10000010, 01100100, 11110101, 1250059509",
            "11001010, 00000010, 01100100, 11110101, -905812747",
            "01001010, 10000010, 11100100, 01110101, 1250092149",
            "11001010, 00000010, 11100100, 01110101, -905780107",
            "11001010, 10000010, 01100100, 01110101, -897424267",
            "01001010, 10000010, 11100100, 11110101, 1250092277",
            "11001010, 00000010, 11100100, 11110101, -905779979",
            "11001010, 10000010, 01100100, 11110101, -897424139",
            "11001010, 10000010, 11100100, 01110101, -897391499",
            "11001010, 10000010, 11100100, 11110101, -897391371",
            "11111111, 11111111, 11111111, 11111111, -1"
        )
        fun `'ByteArray#toIntBigEndian' should correctly convert from Big Endian ByteArray to Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: Int) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2)).toByteArray()
            val result = input.toIntBigEndian()

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1969488458",
            "01001010, 00000010, 01100100, 11110101, 4116972106",
            "01001010, 00000010, 11100100, 01110101, 1977877066",
            "01001010, 10000010, 01100100, 01110101, 1969521226",
            "11001010, 00000010, 01100100, 01110101, 1969488586",
            "01001010, 00000010, 11100100, 11110101, 4125360714",
            "01001010, 10000010, 01100100, 11110101, 4117004874",
            "11001010, 00000010, 01100100, 11110101, 4116972234",
            "01001010, 10000010, 11100100, 01110101, 1977909834",
            "11001010, 00000010, 11100100, 01110101, 1977877194",
            "11001010, 10000010, 01100100, 01110101, 1969521354",
            "01001010, 10000010, 11100100, 11110101, 4125393482",
            "11001010, 00000010, 11100100, 11110101, 4125360842",
            "11001010, 10000010, 01100100, 11110101, 4117005002",
            "11001010, 10000010, 11100100, 01110101, 1977909962",
            "11001010, 10000010, 11100100, 11110101, 4125393610",
            "11111111, 11111111, 11111111, 11111111, 4294967295"
        )
        fun `'ByteArray#toUIntLittleEndian' should correctly convert from Little Endian ByteArray to Unsigned Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2)).toByteArray()
            val result = input.toUIntLittleEndian()

            assertEquals(expected.toUInt(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1241670773",
            "01001010, 00000010, 01100100, 11110101, 1241670901",
            "01001010, 00000010, 11100100, 01110101, 1241703541",
            "01001010, 10000010, 01100100, 01110101, 1250059381",
            "11001010, 00000010, 01100100, 01110101, 3389154421",
            "01001010, 00000010, 11100100, 11110101, 1241703669",
            "01001010, 10000010, 01100100, 11110101, 1250059509",
            "11001010, 00000010, 01100100, 11110101, 3389154549",
            "01001010, 10000010, 11100100, 01110101, 1250092149",
            "11001010, 00000010, 11100100, 01110101, 3389187189",
            "11001010, 10000010, 01100100, 01110101, 3397543029",
            "01001010, 10000010, 11100100, 11110101, 1250092277",
            "11001010, 00000010, 11100100, 11110101, 3389187317",
            "11001010, 10000010, 01100100, 11110101, 3397543157",
            "11001010, 10000010, 11100100, 01110101, 3397575797",
            "11001010, 10000010, 11100100, 11110101, 3397575925",
            "11111111, 11111111, 11111111, 11111111, 4294967295"
        )
        fun `'ByteArray#toUIntBigEndian' should correctly convert from Big Endian ByteArray to Unsigned Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2)).toByteArray()
            val result = input.toUIntBigEndian()

            assertEquals(expected.toUInt(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1969488458",
            "01001010, 00000010, 01100100, 11110101, -177995190",
            "01001010, 00000010, 11100100, 01110101, 1977877066",
            "01001010, 10000010, 01100100, 01110101, 1969521226",
            "11001010, 00000010, 01100100, 01110101, 1969488586",
            "01001010, 00000010, 11100100, 11110101, -169606582",
            "01001010, 10000010, 01100100, 11110101, -177962422",
            "11001010, 00000010, 01100100, 11110101, -177995062",
            "01001010, 10000010, 11100100, 01110101, 1977909834",
            "11001010, 00000010, 11100100, 01110101, 1977877194",
            "11001010, 10000010, 01100100, 01110101, 1969521354",
            "01001010, 10000010, 11100100, 11110101, -169573814",
            "11001010, 00000010, 11100100, 11110101, -169606454",
            "11001010, 10000010, 01100100, 11110101, -177962294",
            "11001010, 10000010, 11100100, 01110101, 1977909962",
            "11001010, 10000010, 11100100, 11110101, -169573686",
            "11111111, 11111111, 11111111, 11111111, -1"
        )
        fun `'UByteArray#toIntLittleEndian' should correctly convert from Little Endian UByteArray to Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: Int) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2))
            val result = input.toIntLittleEndian()

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1241670773",
            "01001010, 00000010, 01100100, 11110101, 1241670901",
            "01001010, 00000010, 11100100, 01110101, 1241703541",
            "01001010, 10000010, 01100100, 01110101, 1250059381",
            "11001010, 00000010, 01100100, 01110101, -905812875",
            "01001010, 00000010, 11100100, 11110101, 1241703669",
            "01001010, 10000010, 01100100, 11110101, 1250059509",
            "11001010, 00000010, 01100100, 11110101, -905812747",
            "01001010, 10000010, 11100100, 01110101, 1250092149",
            "11001010, 00000010, 11100100, 01110101, -905780107",
            "11001010, 10000010, 01100100, 01110101, -897424267",
            "01001010, 10000010, 11100100, 11110101, 1250092277",
            "11001010, 00000010, 11100100, 11110101, -905779979",
            "11001010, 10000010, 01100100, 11110101, -897424139",
            "11001010, 10000010, 11100100, 01110101, -897391499",
            "11001010, 10000010, 11100100, 11110101, -897391371",
            "11111111, 11111111, 11111111, 11111111, -1"
        )
        fun `'UByteArray#toIntBigEndian' should correctly convert from Big Endian UByteArray to Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: Int) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2))
            val result = input.toIntBigEndian()

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1969488458",
            "01001010, 00000010, 01100100, 11110101, 4116972106",
            "01001010, 00000010, 11100100, 01110101, 1977877066",
            "01001010, 10000010, 01100100, 01110101, 1969521226",
            "11001010, 00000010, 01100100, 01110101, 1969488586",
            "01001010, 00000010, 11100100, 11110101, 4125360714",
            "01001010, 10000010, 01100100, 11110101, 4117004874",
            "11001010, 00000010, 01100100, 11110101, 4116972234",
            "01001010, 10000010, 11100100, 01110101, 1977909834",
            "11001010, 00000010, 11100100, 01110101, 1977877194",
            "11001010, 10000010, 01100100, 01110101, 1969521354",
            "01001010, 10000010, 11100100, 11110101, 4125393482",
            "11001010, 00000010, 11100100, 11110101, 4125360842",
            "11001010, 10000010, 01100100, 11110101, 4117005002",
            "11001010, 10000010, 11100100, 01110101, 1977909962",
            "11001010, 10000010, 11100100, 11110101, 4125393610",
            "11111111, 11111111, 11111111, 11111111, 4294967295"
        )
        fun `'UByteArray#toUIntLittleEndian' should correctly convert from Little Endian UByteArray to Unsigned Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2))
            val result = input.toUIntLittleEndian()

            assertEquals(expected.toUInt(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101, 1241670773",
            "01001010, 00000010, 01100100, 11110101, 1241670901",
            "01001010, 00000010, 11100100, 01110101, 1241703541",
            "01001010, 10000010, 01100100, 01110101, 1250059381",
            "11001010, 00000010, 01100100, 01110101, 3389154421",
            "01001010, 00000010, 11100100, 11110101, 1241703669",
            "01001010, 10000010, 01100100, 11110101, 1250059509",
            "11001010, 00000010, 01100100, 11110101, 3389154549",
            "01001010, 10000010, 11100100, 01110101, 1250092149",
            "11001010, 00000010, 11100100, 01110101, 3389187189",
            "11001010, 10000010, 01100100, 01110101, 3397543029",
            "01001010, 10000010, 11100100, 11110101, 1250092277",
            "11001010, 00000010, 11100100, 11110101, 3389187317",
            "11001010, 10000010, 01100100, 11110101, 3397543157",
            "11001010, 10000010, 11100100, 01110101, 3397575797",
            "11001010, 10000010, 11100100, 11110101, 3397575925",
            "11111111, 11111111, 11111111, 11111111, 4294967295"
        )
        fun `'UByteArray#toUIntBigEndian' should correctly convert from Big Endian UByteArray to Unsigned Int`(byte1: String, byte2: String, byte3: String, byte4: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2))
            val result = input.toUIntBigEndian()

            assertEquals(expected.toUInt(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +1314",
            "00100010, 10000101, -31454",
            "10100010, 00000101, +1442",
            "10100010, 10000101, -31326",
            "11111111, 11111111, -1"
        )
        fun `'ByteArray#toShortLittleEndian' should correctly convert from Little Endian ByteArray to Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2)).toByteArray()
            val result = input.toShortLittleEndian()

            assertEquals(expected.toShort(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +8709",
            "00100010, 10000101, +8837",
            "10100010, 00000101, -24059",
            "10100010, 10000101, -23931",
            "11111111, 11111111, -1"
        )
        fun `'ByteArray#toShortBigEndian' should correctly convert from Big Endian ByteArray to Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2)).toByteArray()
            val result = input.toShortBigEndian()

            assertEquals(expected.toShort(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, 1314",
            "00100010, 10000101, 34082",
            "10100010, 00000101, 1442",
            "10100010, 10000101, 34210",
            "11111111, 11111111, 65535"
        )
        fun `'ByteArray#toUShortLittleEndian' should correctly convert from Little Endian ByteArray to Unsigned Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2)).toByteArray()
            val result = input.toUShortLittleEndian()

            assertEquals(expected.toUShort(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, 8709",
            "00100010, 10000101, 8837",
            "10100010, 00000101, 41477",
            "10100010, 10000101, 41605",
            "11111111, 11111111, 65535"
        )
        fun `'ByteArray#toUShortBigEndian' should correctly convert from Big Endian ByteArray to Unsigned Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2)).toByteArray()
            val result = input.toUShortBigEndian()

            assertEquals(expected.toUShort(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +1314",
            "00100010, 10000101, -31454",
            "10100010, 00000101, +1442",
            "10100010, 10000101, -31326",
            "11111111, 11111111, -1"
        )
        fun `'UByteArray#toShortLittleEndian' should correctly convert from Little Endian UByteArray to Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = input.toShortLittleEndian()

            assertEquals(expected.toShort(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +8709",
            "00100010, 10000101, +8837",
            "10100010, 00000101, -24059",
            "10100010, 10000101, -23931",
            "11111111, 11111111, -1"
        )
        fun `'UByteArray#toShortBigEndian' should correctly convert from Big Endian UByteArray to Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = input.toShortBigEndian()

            assertEquals(expected.toShort(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, 1314",
            "00100010, 10000101, 34082",
            "10100010, 00000101, 1442",
            "10100010, 10000101, 34210",
            "11111111, 11111111, 65535"
        )
        fun `'UByteArray#toUShortLittleEndian' should correctly convert from Little Endian UByteArray to Unsigned Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = input.toUShortLittleEndian()

            assertEquals(expected.toUShort(), result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, 8709",
            "00100010, 10000101, 8837",
            "10100010, 00000101, 41477",
            "10100010, 10000101, 41605",
            "11111111, 11111111, 65535"
        )
        fun `'UByteArray#toUShortBigEndian' should correctly convert from Big Endian UByteArray to Unsigned Short`(byte1: String, byte2: String, expected: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = input.toUShortBigEndian()

            assertEquals(expected.toUShort(), result)
        }
    }

    @Nested
    inner class Shortcuts {

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 01100100, 11110101",
            "01001010, 00000010, 11100100, 01110101",
            "01001010, 10000010, 01100100, 01110101",
            "11001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 11100100, 11110101",
            "01001010, 10000010, 01100100, 11110101",
            "11001010, 00000010, 01100100, 11110101",
            "01001010, 10000010, 11100100, 01110101",
            "11001010, 00000010, 11100100, 01110101",
            "11001010, 10000010, 01100100, 01110101",
            "01001010, 10000010, 11100100, 11110101",
            "11001010, 00000010, 11100100, 11110101",
            "11001010, 10000010, 01100100, 11110101",
            "11001010, 10000010, 11100100, 01110101",
            "11001010, 10000010, 11100100, 11110101",
            "11111111, 11111111, 11111111, 11111111"
        )
        fun `'ByteArray#toInt' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String, byte3: String, byte4: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2)).toByteArray()
            assertEquals(input.toIntLittleEndian(), input.toInt())
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 01100100, 11110101",
            "01001010, 00000010, 11100100, 01110101",
            "01001010, 10000010, 01100100, 01110101",
            "11001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 11100100, 11110101",
            "01001010, 10000010, 01100100, 11110101",
            "11001010, 00000010, 01100100, 11110101",
            "01001010, 10000010, 11100100, 01110101",
            "11001010, 00000010, 11100100, 01110101",
            "11001010, 10000010, 01100100, 01110101",
            "01001010, 10000010, 11100100, 11110101",
            "11001010, 00000010, 11100100, 11110101",
            "11001010, 10000010, 01100100, 11110101",
            "11001010, 10000010, 11100100, 01110101",
            "11001010, 10000010, 11100100, 11110101",
            "11111111, 11111111, 11111111, 11111111"
        )
        fun `'ByteArray#toUInt' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String, byte3: String, byte4: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2)).toByteArray()
            assertEquals(input.toUIntLittleEndian(), input.toUInt())
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 01100100, 11110101",
            "01001010, 00000010, 11100100, 01110101",
            "01001010, 10000010, 01100100, 01110101",
            "11001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 11100100, 11110101",
            "01001010, 10000010, 01100100, 11110101",
            "11001010, 00000010, 01100100, 11110101",
            "01001010, 10000010, 11100100, 01110101",
            "11001010, 00000010, 11100100, 01110101",
            "11001010, 10000010, 01100100, 01110101",
            "01001010, 10000010, 11100100, 11110101",
            "11001010, 00000010, 11100100, 11110101",
            "11001010, 10000010, 01100100, 11110101",
            "11001010, 10000010, 11100100, 01110101",
            "11001010, 10000010, 11100100, 11110101",
            "11111111, 11111111, 11111111, 11111111"
        )
        fun `'UByteArray#toInt' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String, byte3: String, byte4: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2))
            assertEquals(input.toIntLittleEndian(), input.toInt())
        }

        @ParameterizedTest
        @CsvSource(
            "01001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 01100100, 11110101",
            "01001010, 00000010, 11100100, 01110101",
            "01001010, 10000010, 01100100, 01110101",
            "11001010, 00000010, 01100100, 01110101",
            "01001010, 00000010, 11100100, 11110101",
            "01001010, 10000010, 01100100, 11110101",
            "11001010, 00000010, 01100100, 11110101",
            "01001010, 10000010, 11100100, 01110101",
            "11001010, 00000010, 11100100, 01110101",
            "11001010, 10000010, 01100100, 01110101",
            "01001010, 10000010, 11100100, 11110101",
            "11001010, 00000010, 11100100, 11110101",
            "11001010, 10000010, 01100100, 11110101",
            "11001010, 10000010, 11100100, 01110101",
            "11001010, 10000010, 11100100, 11110101",
            "11111111, 11111111, 11111111, 11111111"
        )
        fun `'UByteArray#toUInt' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String, byte3: String, byte4: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2), byte3.toUByte(2), byte4.toUByte(2))
            assertEquals(input.toUIntLittleEndian(), input.toUInt())
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101",
            "00100010, 10000101",
            "10100010, 00000101",
            "10100010, 10000101",
            "11111111, 11111111"
        )
        fun `'ByteArray#toShort' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2)).toByteArray()
            assertEquals(input.toShortLittleEndian(), input.toShort())
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101",
            "00100010, 10000101",
            "10100010, 00000101",
            "10100010, 10000101",
            "11111111, 11111111"
        )
        fun `'ByteArray#toUShort' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2)).toByteArray()
            assertEquals(input.toUShortLittleEndian(), input.toUShort())
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101",
            "00100010, 10000101",
            "10100010, 00000101",
            "10100010, 10000101",
            "11111111, 11111111"
        )
        fun `'UByteArray#toShort' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            assertEquals(input.toShortLittleEndian(), input.toShort())
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101",
            "00100010, 10000101",
            "10100010, 00000101",
            "10100010, 10000101",
            "11111111, 11111111"
        )
        fun `'UByteArray#toUShort' shortcut should use the little endian order to convert the value`(byte1: String, byte2: String) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            assertEquals(input.toUShortLittleEndian(), input.toUShort())
        }
    }
}