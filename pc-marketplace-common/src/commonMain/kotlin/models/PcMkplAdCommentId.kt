package ru.gorbunovas.pcmarketplace.common.models

@JvmInline
value class PcMkplAdCommentId (private val id: String) {
    companion object {
        val NONE = PcMkplAdCommentId("")
    }
}