package dev.disaverio.wlkreader.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder


// SHORTCUTS: to Int and UInt, from ByteArray

fun ByteArray.toInt() =
    this.toIntLittleEndian()

fun ByteArray.toUInt() =
    this.toUIntLittleEndian()

fun ByteArray.toIntUnsigned(): Int {
    val value = this.toUIntLittleEndian()

    if (value > Int.MAX_VALUE.toUInt())
        throw Exception("Value overflow")

    return value.toInt()
}


// SHORTCUTS: to Int and UInt, from UByteArray

fun UByteArray.toInt() =
    this.toIntLittleEndian()

fun UByteArray.toUInt() =
    this.toUIntLittleEndian()

fun UByteArray.toIntUnsigned(): Int {
    val value = this.toUIntLittleEndian()

    if (value > Int.MAX_VALUE.toUInt())
        throw Exception("Value overflow")

    return value.toInt()
}


// SHORTCUTS: to Short and UShort, from ByteArray

fun ByteArray.toShort() =
    this.toShortLittleEndian()

fun ByteArray.toUShort() =
    this.toUShortLittleEndian()

fun ByteArray.toShortUnsigned(): Short {
    val value = this.toUShortLittleEndian()

    if (value > Short.MAX_VALUE.toUShort())
        throw Exception("Value overflow")

    return value.toShort()
}


// SHORTCUTS: to Short and UShort, from UByteArray

fun UByteArray.toShort() =
    this.toShortLittleEndian()

fun UByteArray.toUShort() =
    this.toUShortLittleEndian()

fun UByteArray.toShortUnsigned(): Short {
    val value = this.toUShortLittleEndian()

    if (value > Short.MAX_VALUE.toUShort())
        throw Exception("Value overflow")

    return value.toShort()
}


// to Int and UInt, from ByteArray

fun ByteArray.toIntLittleEndian(): Int {
    require(this.size == 4)
    return ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN).int
}

fun ByteArray.toIntBigEndian(): Int {
    require(this.size == 4)
    return ByteBuffer.wrap(this).order(ByteOrder.BIG_ENDIAN).int
}

fun ByteArray.toUIntLittleEndian(): UInt {
    require(this.size == 4)
    return this.foldRight(0.toUInt()) { next, acc -> next.toUByte().toUInt() or (acc shl 8) }
}

fun ByteArray.toUIntBigEndian(): UInt {
    require(this.size == 4)
    return this.fold(0.toUInt()) { acc, next -> next.toUByte().toUInt() or (acc shl 8) }
}


// to Int and UInt, from UByteArray

fun UByteArray.toIntLittleEndian(): Int {
    require(this.size == 4)
    return ByteBuffer.wrap(this.toByteArray()).order(ByteOrder.LITTLE_ENDIAN).int
}

fun UByteArray.toIntBigEndian(): Int {
    require(this.size == 4)
    return ByteBuffer.wrap(this.toByteArray()).order(ByteOrder.BIG_ENDIAN).int
}

fun UByteArray.toUIntLittleEndian(): UInt {
    require(this.size == 4)
    return this.foldRight(0.toUInt()) { next, acc -> next.toUInt() or (acc shl 8) }
}

fun UByteArray.toUIntBigEndian(): UInt {
    require(this.size == 4)
    return this.fold(0.toUInt()) { acc, next -> next.toUInt() or (acc shl 8) }
}


// to Short and UShort, from ByteArray

fun ByteArray.toShortLittleEndian(): Short {
    require(this.size == 2)
    return ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN).short
}

fun ByteArray.toShortBigEndian(): Short {
    require(this.size == 2)
    return ByteBuffer.wrap(this).order(ByteOrder.BIG_ENDIAN).short
}

fun ByteArray.toUShortLittleEndian(): UShort {
    require(this.size == 2)
    return this.foldRight(0.toUInt()) { next, acc -> next.toUByte().toUInt() or (acc shl 8) }.toUShort()
}

fun ByteArray.toUShortBigEndian(): UShort {
    require(this.size == 2)
    return this.fold(0.toUInt()) { acc, next -> next.toUByte().toUInt() or (acc shl 8) }.toUShort()
}


// to Short and UShort, from UByteArray

fun UByteArray.toShortLittleEndian(): Short {
    require(this.size == 2)
    return ByteBuffer.wrap(this.toByteArray()).order(ByteOrder.LITTLE_ENDIAN).short
}

fun UByteArray.toShortBigEndian(): Short {
    require(this.size == 2)
    return ByteBuffer.wrap(this.toByteArray()).order(ByteOrder.BIG_ENDIAN).short
}

fun UByteArray.toUShortLittleEndian(): UShort {
    require(this.size == 2)
    return this.foldRight(0.toUInt()) { next, acc -> next.toUInt() or (acc shl 8) }.toUShort()
}

fun UByteArray.toUShortBigEndian(): UShort {
    require(this.size == 2)
    return this.fold(0.toUInt()) { acc, next -> next.toUInt() or (acc shl 8) }.toUShort()
}