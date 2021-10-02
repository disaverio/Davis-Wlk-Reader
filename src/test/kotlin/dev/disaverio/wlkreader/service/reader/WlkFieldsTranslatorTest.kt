package dev.disaverio.wlkreader.service.reader

import dev.disaverio.wlkreader.types.WindDirection as WindDirectionEnum
import org.junit.Assert
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class WlkFieldsTranslatorTest {

    @Nested
    inner class DimensionlessValues {

        @Test
        fun `getDimensionlessValue should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getDimensionlessValue(wlkValue) }
        }

        @Test
        fun `getDimensionlessValueUnsigned should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getDimensionlessValueUnsigned(wlkValue) }
        }

        @Test
        fun `getDimensionlessValueFromTenths should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getDimensionlessValueFromTenths(wlkValue) }
        }

        @Test
        fun `getDimensionlessValueFromCents should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getDimensionlessValueFromCents(wlkValue) }
        }

        @Test
        fun `getDimensionlessValueFromThousandths should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getDimensionlessValueFromThousandths(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +1314",
            "00100010, 10000101, -31454",
            "10100010, 00000101, +1442",
            "10100010, 10000101, -31326",
            "11111111, 11111111, -1"
        )
        fun `getDimensionlessValue should use the little endian order to convert the value to a signed int`(byte1: String, byte2: String, expected: Int) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getDimensionlessValue(input)

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, 1314",
            "00100010, 10000101, 34082",
            "10100010, 00000101, 1442",
            "10100010, 10000101, 34210",
            "11111111, 11111111, 65535"
        )
        fun `getDimensionlessValueUnsigned should use the little endian order to convert the value to an unsigned int`(byte1: String, byte2: String, expected: Int) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getDimensionlessValueUnsigned(input)

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +131.4",
            "00100010, 10000101, -3145.4",
            "10100010, 00000101, +144.2",
            "10100010, 10000101, -3132.6",
            "11111111, 11111111, -0.1"
        )
        fun `getDimensionlessValueFromTenths should use the little endian order to convert the value to a signed int scaled by 10`(byte1: String, byte2: String, expected: Double) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getDimensionlessValueFromTenths(input)

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +13.14",
            "00100010, 10000101, -314.54",
            "10100010, 00000101, +14.42",
            "10100010, 10000101, -313.26",
            "11111111, 11111111, -0.01"
        )
        fun `getDimensionlessValueFromCents should use the little endian order to convert the value to a signed int scaled by 100`(byte1: String, byte2: String, expected: Double) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getDimensionlessValueFromCents(input)

            assertEquals(expected, result)
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +1.314",
            "00100010, 10000101, -31.454",
            "10100010, 00000101, +1.442",
            "10100010, 10000101, -31.326",
            "11111111, 11111111, -0.001"
        )
        fun `getDimensionlessValueFromThousandths should use the little endian order to convert the value to a signed int scaled by 1000`(byte1: String, byte2: String, expected: Double) {
            val input = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getDimensionlessValueFromThousandths(input)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class DataSpan {

        @Test
        fun `getDataSpan should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getDataSpan(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +1314",
            "00100010, 10000101, -31454",
            "10100010, 00000101, +1442",
            "10100010, 10000101, -31326",
            "11111111, 11111111, -1"
        )
        fun `getDataSpan should return a dimensionless value`(byte1: String, byte2: String, expected: Int) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getDataSpan(wlkValue)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class Temperature {

        @Test
        fun `getTemperature should reject temperature byte array of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getTemperature(wlkValue) }
        }

        @Test
        fun `getTemperature should return null if value is equal to MIN_VALUE`() {
            val wlkValue = ubyteArrayOf("00000000".toUByte(2), "10000000".toUByte(2))
            assertNull(WlkFieldsTranslator.getTemperature(wlkValue))
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +131.4",
            "00100010, 10000101, -3145.4",
            "10100010, 00000101, +144.2",
            "10100010, 10000101, -3132.6",
            "11111111, 11111111, -0.1"
        )
        fun `getTemperature should return a Temperature object initialized from tenths of Fahrenheit unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getTemperature(wlkValue)

            assertNotNull(result)
            assertEquals(expected, result.fahrenheit)
        }
    }

    @Nested
    inner class Humidity {

        @Test
        fun `getHumidity should reject temperature byte array of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getHumidity(wlkValue) }
        }

        @Test
        fun `getHumidity should return null if value is equal to MIN_VALUE`() {
            val wlkValue = ubyteArrayOf("00000000".toUByte(2), "10000000".toUByte(2))
            assertNull(WlkFieldsTranslator.getHumidity(wlkValue))
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +131.4",
            "00100010, 10000101, -3145.4",
            "10100010, 00000101, +144.2",
            "10100010, 10000101, -3132.6",
            "11111111, 11111111, -0.1"
        )
        fun `getHumidity should return a dimensionless value converted from tenths`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getHumidity(wlkValue)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class Speed {

        @Test
        fun `getSpeed should reject speed byte array of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getSpeed(wlkValue) }
        }

        @Test
        fun `getSpeed should return null if value is equal to MIN_VALUE`() {
            val wlkValue = ubyteArrayOf("00000000".toUByte(2), "10000000".toUByte(2))
            assertNull(WlkFieldsTranslator.getSpeed(wlkValue))
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +131.4",
            "00100010, 10000101, -3145.4",
            "10100010, 00000101, +144.2",
            "10100010, 10000101, -3132.6",
            "11111111, 11111111, -0.1"
        )
        fun `getSpeed should return a Speed object initialized from tenths of Milesph unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getSpeed(wlkValue)

            assertNotNull(result)
            assertEquals(expected, result.milesph)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class WindDirection {

        @Test
        fun `getWindDirection should reject byte arrays of a size other than 1`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getWindDirection(wlkValue) }
        }

        @Test
        fun `getWindDirection should return null if value is equal to 255`() {
            val wlkValue = ubyteArrayOf("11111111".toUByte(2))
            assertNull(WlkFieldsTranslator.getWindDirection(wlkValue))
        }

        @ParameterizedTest
        @MethodSource("getLegitValuesAsArguments")
        fun `getWindDirection should return the right WindDirection enum for input in the range 0 to 15`(byte: String, direction: WindDirectionEnum) {
            val wlkValue = ubyteArrayOf(byte.toUByte(2))
            val result = WlkFieldsTranslator.getWindDirection(wlkValue)

            assertNotNull(result)
            assertEquals(direction, result)
        }

        @ParameterizedTest
        @MethodSource("getInvalidValuesAsArguments")
        fun `getWindDirection should throw for input not in the range 0 to 15`(value: Int) {
            val wlkValue = ubyteArrayOf(value.toUByte())
            assertThrows<Exception> { WlkFieldsTranslator.getWindDirection(wlkValue) }
        }

        private fun getInvalidValuesAsArguments(): IntStream {
            return IntStream.range(16, 255)
        }

        private fun getLegitValuesAsArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of("00000000", WindDirectionEnum.N),
                Arguments.of("00000001", WindDirectionEnum.NNE),
                Arguments.of("00000010", WindDirectionEnum.NE),
                Arguments.of("00000011", WindDirectionEnum.ENE),
                Arguments.of("00000100", WindDirectionEnum.E),
                Arguments.of("00000101", WindDirectionEnum.ESE),
                Arguments.of("00000110", WindDirectionEnum.SE),
                Arguments.of("00000111", WindDirectionEnum.SSE),
                Arguments.of("00001000", WindDirectionEnum.S),
                Arguments.of("00001001", WindDirectionEnum.SSW),
                Arguments.of("00001010", WindDirectionEnum.SW),
                Arguments.of("00001011", WindDirectionEnum.WSW),
                Arguments.of("00001100", WindDirectionEnum.W),
                Arguments.of("00001101", WindDirectionEnum.WNW),
                Arguments.of("00001110", WindDirectionEnum.NW),
                Arguments.of("00001111", WindDirectionEnum.NNW)
            )
    }

    @Nested
    inner class WindRun {

        @Test
        fun `getWindRun should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getWindRun(wlkValue) }
        }

        @Test
        fun `getWindRun should return null if value is equal to MIN_VALUE`() {
            val wlkValue = ubyteArrayOf("00000000".toUByte(2), "10000000".toUByte(2))
            assertNull(WlkFieldsTranslator.getWindRun(wlkValue))
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +131.4",
            "00100010, 10000101, -3145.4",
            "10100010, 00000101, +144.2",
            "10100010, 10000101, -3132.6",
            "11111111, 11111111, -0.1"
        )
        fun `getWindRun should return a Distance object initialized from tenths of Miles unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getWindRun(wlkValue)

            assertNotNull(result)
            assertEquals(expected, result.miles)
        }
    }

    @Nested
    inner class Pressure {

        @Test
        fun `getPressure should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getPressure(wlkValue) }
        }

        @Test
        fun `getPressure should return null if value is equal to MIN_VALUE`() {
            val wlkValue = ubyteArrayOf("00000000".toUByte(2), "10000000".toUByte(2))
            assertNull(WlkFieldsTranslator.getPressure(wlkValue))
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +1.314",
            "00100010, 10000101, -31.454",
            "10100010, 00000101, +1.442",
            "10100010, 10000101, -31.326",
            "11111111, 11111111, -0.001"
        )
        fun `getPressure should return a Pressure object initialized from thousandths of InHg unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getPressure(wlkValue)

            assertNotNull(result)
            assertEquals(expected, result.inHg)
        }
    }

    @Nested
    inner class UvIndex {

        @Test
        fun `getUvIndex should reject byte arrays of a size other than 1`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getUvIndex(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "00100101, 3.7",
            "10100101, 16.5",
            "11111111, 25.5"
        )
        fun `getUvIndex should return an unsigned dimensionless value converted from tenths`(byte: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte.toUByte(2))
            val result = WlkFieldsTranslator.getUvIndex(wlkValue)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class UvMedIndex {

        @Test
        fun `getUvMedIndex should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getUvMedIndex(wlkValue) }
        }

        @Test
        fun `getUvMedIndex should return null if value is equal to MIN_VALUE`() {
            val wlkValue = ubyteArrayOf("00000000".toUByte(2), "10000000".toUByte(2))
            assertNull(WlkFieldsTranslator.getUvMedIndex(wlkValue))
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +131.4",
            "00100010, 10000101, -3145.4",
            "10100010, 00000101, +144.2",
            "10100010, 10000101, -3132.6",
            "11111111, 11111111, -0.1"
        )
        fun `getUvMedIndex should return a dimensionless value converted from tenths`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getUvMedIndex(wlkValue)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class Precipitation {

        @Test
        fun `getPrecipitation should reject precipitation byte array of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getPrecipitation(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +1.314",
            "00100010, 10000101, -31.454",
            "10100010, 00000101, +1.442",
            "10100010, 10000101, -31.326",
            "11111111, 11111111, -0.001"
        )
        fun `getPrecipitation should return a Precipitation object initialized from thousandths of Inch unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getPrecipitation(wlkValue)

            assertNotNull(result)
            assertEquals(expected, result.inches)
        }
    }

    @Nested
    inner class PrecipitationFromClicksQuantity {

        @Test
        fun `getPrecipitationFromClicksQuantity should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getPrecipitationFromClicksQuantity(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "01010110, 00000001, 868.68",
            "01010110, 00010001, 86.868",
            "01010110, 00100001, 68.4",
            "01010110, 00110001, 342",
            "01010110, 01100001, 34.2"
        )
        fun `getPrecipitationFromClicksQuantity should return a Precipitation object initialized from Mm unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getPrecipitationFromClicksQuantity(wlkValue)

            assertNotNull(result)
            Assert.assertEquals(expected, result.mm, 0.000000001)
        }
    }

    @Nested
    inner class RainRate {

        @Test
        fun `getRainRate should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getRainRate(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +13.14",
            "00100010, 10000101, -314.54",
            "10100010, 00000101, +14.42",
            "10100010, 10000101, -313.26",
            "11111111, 11111111, -0.01"
        )
        fun `getRainRate should return a RainRate object initialized from cents of InchesPerHour unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getRainRate(wlkValue)

            assertNotNull(result)
            assertEquals(expected, result.inchesph)
        }
    }

    @Nested
    inner class RainRateFromClicksQuantity {

        @Test
        fun `getRainRateFromClicksNumber should reject 'clicksQtyAndCollectorCode' byte arrays of a size other than 2`() {
            val clicksQtyAndCollectorCodeWlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            val clicksPerHour = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getRainRateFromClicksNumber(clicksQtyAndCollectorCodeWlkValue, clicksPerHour) }
        }

        @Test
        fun `getRainRateFromClicksNumber should reject 'clicksPerHour' byte arrays of a size other than 2`() {
            val clicksQtyAndCollectorCodeWlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
            val clicksPerHour = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getRainRateFromClicksNumber(clicksQtyAndCollectorCodeWlkValue, clicksPerHour) }
        }

        @ParameterizedTest
        @CsvSource(
            "10010110, 00000110, 01010110, 00000001, 868.68",
            "10010110, 00010110, 01010110, 00000001, 86.868",
            "10010110, 00100110, 01010110, 00000001, 68.4",
            "10010110, 00110110, 01010110, 00000001, 342",
            "10010110, 01100110, 01010110, 00000001, 34.2"
        )
        fun `getRainRateFromClicksNumber should return a RainRate object initialized from MmPerHour unit, on legit values`(byte1: String, byte2: String, byte3: String, byte4: String, expected: Double) {
            val clicksQtyAndCollectorCodeWlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val clicksPerHour = ubyteArrayOf(byte3.toUByte(2), byte4.toUByte(2))
            val result = WlkFieldsTranslator.getRainRateFromClicksNumber(clicksQtyAndCollectorCodeWlkValue, clicksPerHour)

            assertNotNull(result)
            Assert.assertEquals(expected, result.mmph, 0.000000001)
        }
    }

    @Nested
    inner class DeltaTemperature {

        @Test
        fun `getDeltaTemperature should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getDeltaTemperature(wlkValue) }
        }

        @Test
        fun `getDeltaTemperature should return null if value is equal to MIN_VALUE`() {
            val wlkValue = ubyteArrayOf("00000000".toUByte(2), "10000000".toUByte(2))
            assertNull(WlkFieldsTranslator.getDeltaTemperature(wlkValue))
        }

        @ParameterizedTest
        @CsvSource(
            "00100010, 00000101, +131.4",
            "00100010, 10000101, -3145.4",
            "10100010, 00000101, +144.2",
            "10100010, 10000101, -3132.6",
            "11111111, 11111111, -0.1"
        )
        fun `getDeltaTemperature should return a DeltaTemperature object initialized from tenths of Fahrenheit unit, on legit values`(byte1: String, byte2: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getDeltaTemperature(wlkValue)

            assertNotNull(result)
            assertEquals(expected, result.fahrenheit)
        }
    }

    @Nested
    inner class RecordTime {

        @Test
        fun `getRecordTime should reject byte arrays of a size other than 2`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getRecordTime(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "10100000, 00000101, 23, 59", // tot minutes: 1440
            "00000001, 00000000, 00, 00", // tot minutes: 1
            "11011001, 00000010, 12, 8" // tot minutes: 729
        )
        fun `getRecordTime should return a LocalTime object when in input is given a byte array containing the number of minutes past midnight plus 1`(byte1: String, byte2: String, expectedHour: Int, expectedMinute: Int) {
            val wlkValue = ubyteArrayOf(byte1.toUByte(2), byte2.toUByte(2))
            val result = WlkFieldsTranslator.getRecordTime(wlkValue)

            assertEquals(expectedHour, result.hour)
            assertEquals(expectedMinute, result.minute)
        }
    }

    @Nested
    inner class ArchiveInterval {

        @Test
        fun `getArchiveInterval should reject byte arrays of a size other than 1`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getArchiveInterval(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "00000001, 1",
            "00100101, 37",
            "10100101, 165",
            "11111111, 255"
        )
        fun `getArchiveInterval should return an unsigned dimensionless value`(byte: String, expected: Int) {
            val wlkValue = ubyteArrayOf(byte.toUByte(2))
            val result = WlkFieldsTranslator.getArchiveInterval(wlkValue)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class Evapotranspiration {

        @Test
        fun `getEvapotranspiration should reject byte arrays of a size other than 1`() {
            val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
            assertThrows<Exception> { WlkFieldsTranslator.getEvapotranspiration(wlkValue) }
        }

        @ParameterizedTest
        @CsvSource(
            "00000001, 0.001",
            "00100101, 0.037",
            "10100101, 0.165",
            "11111111, 0.255"
        )
        fun `getEvapotranspiration should return an unsigned dimensionless value converted from thousandths`(byte: String, expected: Double) {
            val wlkValue = ubyteArrayOf(byte.toUByte(2))
            val result = WlkFieldsTranslator.getEvapotranspiration(wlkValue)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class TimeValues {

        @Nested
        inner class GetHiOutTempTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiOutTempTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "11111111, 01010000, 00001111, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "11111111, 01010000, 00000111, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "00000000, 01010000, 00001000, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 1st byte combined with the one in rightmost nibble of 3rd byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiOutTempTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 23, 59"
            )
            fun `should use 1st byte and rightmost nibble of 3rd byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiOutTempTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowOutTempTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowOutTempTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 11111111, 11110101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 11111111, 01110101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 00000000, 10000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 2nd byte combined with the one in leftmost nibble of 3rd byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowOutTempTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 1, 19"
            )
            fun `should use 2nd byte and leftmost nibble of 3rd byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getLowOutTempTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiInTempTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiInTempTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 11111111, 11010000, 00101111, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 11111111, 11010000, 00100111, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 00000000, 11010000, 00101000, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 4th byte combined with the one in rightmost nibble of 6th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiInTempTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 22, 39"
            )
            fun `should use 4th byte and rightmost nibble of 6th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiInTempTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowInTempTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowInTempTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11111111, 11110101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11111111, 01110101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 00000000, 10000101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 5th byte combined with the one in leftmost nibble of 6th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowInTempTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 11, 59"
            )
            fun `should use 5th byte and leftmost nibble of 6th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getLowInTempTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiChillTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiChillTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 11111111, 00100000, 00111111, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 11111111, 00100000, 00110111, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00111000, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 7th byte combined with the one in rightmost nibble of 9th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiChillTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 21, 19"
            )
            fun `should use 7th byte and rightmost nibble of 9th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiChillTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowChillTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowChillTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 11111111, 11110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 11111111, 01110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00000000, 10000101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 8th byte combined with the one in leftmost nibble of 9th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowChillTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 13, 19"
            )
            fun `should use 8th byte and leftmost nibble of 9th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getLowChillTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiDewTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiDewTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 11111111, 01000000, 00011111, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 11111111, 01000000, 00010111, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 00000000, 01000000, 00011000, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 10th byte combined with the one in rightmost nibble of 12th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiDewTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 19, 59"
            )
            fun `should use 10th byte and rightmost nibble of 12th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiDewTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowDewTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowDewTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 11111111, 11110100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 11111111, 01110100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 00000000, 10000100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 11th byte combined with the one in leftmost nibble of 12th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowDewTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 5, 19"
            )
            fun `should use 11th byte and leftmost nibble of 12th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getLowDewTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiOutHumTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiOutHumTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 11111111, 10100000, 00001111, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 11111111, 10100000, 00000111, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 00000000, 10100000, 00001000, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 13th byte combined with the one in rightmost nibble of 15th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiOutHumTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 18, 39"
            )
            fun `should use 13th byte and rightmost nibble of 15th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiOutHumTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowOutHumTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowOutHumTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 11111111, 11110100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 11111111, 01110100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 00000000, 10000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 14th byte combined with the one in leftmost nibble of 15th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowOutHumTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 2, 39"
            )
            fun `should use 14th byte and leftmost nibble of 15th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getLowOutHumTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiInHumTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiInHumTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 11111111, 11000000, 00111111, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 11111111, 11000000, 00110111, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00000000, 11000000, 00111000, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 16th byte combined with the one in rightmost nibble of 18th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiInHumTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 17, 19"
            )
            fun `should use 16th byte and rightmost nibble of 18th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiInHumTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowInHumTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowInHumTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11111111, 11110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11111111, 01110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 00000000, 10000100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 17th byte combined with the one in leftmost nibble of 18th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowInHumTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 15, 59"
            )
            fun `should use 17th byte and leftmost nibble of 18th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getLowInHumTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiBarTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiBarTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11111111, 10010000, 00011111, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11111111, 10010000, 00010111, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 00000000, 10010000, 00011000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 19th byte combined with the one in rightmost nibble of 21th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiBarTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 3, 59"
            )
            fun `should use 19th byte and rightmost nibble of 21th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiBarTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowBarTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowBarTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 11111111, 11110000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 11111111, 01110000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 00000000, 10000000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 20th byte combined with the one in leftmost nibble of 21th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowBarTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 6, 39"
            )
            fun `should use 20th byte and leftmost nibble of 21th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getLowBarTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiSpeedTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiSpeedTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 11111111, 11100000, 00011111, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 11111111, 11100000, 00010111, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00000000, 11100000, 00011000, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 22th byte combined with the one in rightmost nibble of 24th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiSpeedTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 9, 19"
            )
            fun `should use 22th byte and rightmost nibble of 24th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiSpeedTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHi10MinSpeedTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHi10MinSpeedTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11111111, 11110010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11111111, 01110010, 01110000, 10000000, 00100011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 00000000, 10000010, 01110000, 10000000, 00100011"
            )
            fun `should return null if value in 23th byte combined with the one in leftmost nibble of 24th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHi10MinSpeedTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 7, 59"
            )
            fun `should use 23th byte and leftmost nibble of 24th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHi10MinSpeedTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiRainRateTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiRainRateTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 11111111, 10000000, 00101111",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 11111111, 10000000, 00100111",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 00000000, 10000000, 00101000"
            )
            fun `should return null if value in 25th byte combined with the one in rightmost nibble of 27th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiRainRateTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 14, 39"
            )
            fun `should use 25th byte and rightmost nibble of 27th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiRainRateTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiUVTime {

            @Test
            fun `should reject byte arrays of a size other than 27`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiUVTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 11111111, 11110011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 11111111, 01110011",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 00000000, 10000011"
            )
            fun `should return null if value in 26th byte combined with the one in leftmost nibble of 27th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiUVTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 00010000, 11000000, 00110100, 11110000, 10010000, 00010000, 00110000, 11100000, 00010010, 01110000, 10000000, 00100011, 10, 39"
            )
            fun `should use 26th byte and leftmost nibble of 27th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, b16: String, b17: String, b18: String, b19: String, b20: String, b21: String, b22: String, b23: String, b24: String, b25: String, b26: String, b27: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), b16.toUByte(2), b17.toUByte(2), b18.toUByte(2), b19.toUByte(2), b20.toUByte(2), b21.toUByte(2), b22.toUByte(2), b23.toUByte(2), b24.toUByte(2), b25.toUByte(2), b26.toUByte(2), b27.toUByte(2))

                val result = WlkFieldsTranslator.getHiUVTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiHeatTime {

            @Test
            fun `should reject byte arrays of a size other than 15`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiHeatTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 11111111, 11110101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 11111111, 01110101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 00000000, 10000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100"
            )
            fun `should return null if value in 2nd byte combined with the one in leftmost nibble of 3rd byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiHeatTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 1, 19"
            )
            fun `should use 2nd byte and leftmost nibble of 3rd byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))

                val result = WlkFieldsTranslator.getHiHeatTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowHeatTime {

            @Test
            fun `should reject byte arrays of a size other than 15`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowHeatTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 11111111, 11010000, 00101111, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 11111111, 11010000, 00100111, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 00000000, 11010000, 00101000, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100"
            )
            fun `should return null if value in 4th byte combined with the one in rightmost nibble of 6th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowHeatTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 22, 39"
            )
            fun `should use 4th byte and rightmost nibble of 6th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))

                val result = WlkFieldsTranslator.getLowHeatTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiTHSWTime {

            @Test
            fun `should reject byte arrays of a size other than 15`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiTHSWTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11111111, 11110101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 11111111, 01110101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 00000000, 10000101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100"
            )
            fun `should return null if value in 5th byte combined with the one in leftmost nibble of 6th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiTHSWTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 11, 59"
            )
            fun `should use 5th byte and leftmost nibble of 6th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))

                val result = WlkFieldsTranslator.getHiTHSWTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowTHSWTime {

            @Test
            fun `should reject byte arrays of a size other than 15`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowTHSWTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 11111111, 00100000, 00111111, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 11111111, 00100000, 00110111, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00111000, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100"
            )
            fun `should return null if value in 7th byte combined with the one in rightmost nibble of 9th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))
                assertNull(WlkFieldsTranslator.getLowTHSWTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 21, 19"
            )
            fun `should use 7th byte and rightmost nibble of 9th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))

                val result = WlkFieldsTranslator.getLowTHSWTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetHiTHWTime {

            @Test
            fun `should reject byte arrays of a size other than 15`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getHiTHWTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 11111111, 11110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 11111111, 01110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00000000, 10000101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100"
            )
            fun `should return null if value in 8th byte combined with the one in leftmost nibble of 9th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))
                assertNull(WlkFieldsTranslator.getHiTHWTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 13, 19"
            )
            fun `should use 8th byte and leftmost nibble of 9th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))

                val result = WlkFieldsTranslator.getHiTHWTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        inner class GetLowTHWTime {

            @Test
            fun `should reject byte arrays of a size other than 15`() {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getLowTHWTime(wlkValue) }
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 11111111, 01000000, 00011111, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 11111111, 01000000, 00010111, 01100000, 10100000, 00000100",
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 00000000, 01000000, 00011000, 01100000, 10100000, 00000100"
            )
            fun `should return null if value in 10th byte combined with the one in rightmost nibble of 12th byte is equal to 4095 or 2047 or 2048`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2), )
                assertNull(WlkFieldsTranslator.getLowTHWTime(wlkValue))
            }

            @ParameterizedTest
            @CsvSource(
                "10100000, 01010000, 00000101, 01010000, 11010000, 00100101, 00000000, 00100000, 00110101, 10110000, 01000000, 00010100, 01100000, 10100000, 00000100, 19, 59"
            )
            fun `should use 10th byte and rightmost nibble of 12th byte of the array to get time value`(b1: String, b2: String, b3: String, b4: String, b5: String, b6: String, b7: String, b8: String, b9: String, b10: String, b11: String, b12: String, b13: String, b14: String, b15: String, expectedHour: Int, expectedMinute: Int) {
                val wlkValue = ubyteArrayOf(b1.toUByte(2), b2.toUByte(2), b3.toUByte(2), b4.toUByte(2), b5.toUByte(2), b6.toUByte(2), b7.toUByte(2), b8.toUByte(2), b9.toUByte(2), b10.toUByte(2), b11.toUByte(2), b12.toUByte(2), b13.toUByte(2), b14.toUByte(2), b15.toUByte(2))

                val result = WlkFieldsTranslator.getLowTHWTime(wlkValue)

                assertNotNull(result)
                assertEquals(expectedHour, result.hour)
                assertEquals(expectedMinute, result.minute)
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class GetMinutesAsDominantDirection {

            @ParameterizedTest
            @EnumSource(WindDirectionEnum::class)
            fun `should reject byte arrays of a size other than 24`(direction: WindDirectionEnum) {
                val wlkValue = ubyteArrayOf("01001011".toUByte(2), "01001011".toUByte(2))
                assertThrows<Exception> { WlkFieldsTranslator.getMinutesAsDominantDirection(direction, wlkValue) }
            }

            @ParameterizedTest
            @MethodSource("getCheckDirectionTestArguments")
            fun `for each direction, should correctly extract the amount of minutes from time values array`(direction: WindDirectionEnum, expectedPackedTime: Int) {
                val b = arrayOf("10100000", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010")
                val wlkValue = ubyteArrayOf(b[0].toUByte(2), b[1].toUByte(2), b[2].toUByte(2), b[3].toUByte(2), b[4].toUByte(2), b[5].toUByte(2), b[6].toUByte(2), b[7].toUByte(2), b[8].toUByte(2), b[9].toUByte(2), b[10].toUByte(2), b[11].toUByte(2), b[12].toUByte(2), b[13].toUByte(2), b[14].toUByte(2), b[15].toUByte(2), b[16].toUByte(2), b[17].toUByte(2), b[18].toUByte(2), b[19].toUByte(2), b[20].toUByte(2), b[21].toUByte(2), b[22].toUByte(2), b[23].toUByte(2))

                val result = WlkFieldsTranslator.getMinutesAsDominantDirection(direction, wlkValue)
                assertEquals(expectedPackedTime, result)
            }

            private fun getCheckDirectionTestArguments(): Stream<Arguments> =
                Stream.of(
                    Arguments.of(WindDirectionEnum.N, 1440),
                    Arguments.of(WindDirectionEnum.NNE, 80),
                    Arguments.of(WindDirectionEnum.NE, 1360),
                    Arguments.of(WindDirectionEnum.ENE, 720),
                    Arguments.of(WindDirectionEnum.E, 1280),
                    Arguments.of(WindDirectionEnum.ESE, 800),
                    Arguments.of(WindDirectionEnum.SE, 1200),
                    Arguments.of(WindDirectionEnum.SSE, 320),
                    Arguments.of(WindDirectionEnum.S, 1120),
                    Arguments.of(WindDirectionEnum.SSW, 160),
                    Arguments.of(WindDirectionEnum.SW, 1040),
                    Arguments.of(WindDirectionEnum.WSW, 960),
                    Arguments.of(WindDirectionEnum.W, 240),
                    Arguments.of(WindDirectionEnum.WNW, 400),
                    Arguments.of(WindDirectionEnum.NW, 560),
                    Arguments.of(WindDirectionEnum.NNW, 480)
                )
        }
    }
}