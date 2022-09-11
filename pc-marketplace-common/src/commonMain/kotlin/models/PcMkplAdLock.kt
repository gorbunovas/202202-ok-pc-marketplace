package ru.gorbunovas.pcmarketplace.common.models

@JvmInline
value class PcMkplAdLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = PcMkplAdLock("")
    }
}
