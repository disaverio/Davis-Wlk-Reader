package dev.disaverio.wlkreader.types

import dev.disaverio.wlkreader.types.units.UnitSystem

abstract class TypeWithUnitSystem<T> {

    abstract fun getDefault(): T

    abstract fun getSi(): T

    abstract fun getImperial(): T

    fun toString(unitSystem: UnitSystem? = null, format: String = "%.2f") =
        String.format(format, when(unitSystem) {
            null -> getDefault()
            UnitSystem.SI -> getSi()
            UnitSystem.IMPERIAL -> getImperial()
        })
}