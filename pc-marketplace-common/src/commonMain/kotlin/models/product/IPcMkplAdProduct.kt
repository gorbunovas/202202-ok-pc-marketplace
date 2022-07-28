package ru.gorbunovas.pcmarketplace.common.models.product

sealed interface IPcMkplAdProduct {
    fun deepCopy(): IPcMkplAdProduct
    companion object {
        val NONE = PcMkplAdProductNone
    }
}

object PcMkplAdProductNone: IPcMkplAdProduct {
    override fun deepCopy(): IPcMkplAdProduct = this
}
