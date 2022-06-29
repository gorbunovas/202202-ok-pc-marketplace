package ru.gorbunovas.pcmarketplace.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class PcMkplUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = PcMkplUserId("")
    }
}
