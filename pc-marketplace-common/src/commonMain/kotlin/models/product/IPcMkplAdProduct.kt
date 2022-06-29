package ru.gorbunovas.pcmarketplace.common.models.product

sealed interface IPcMkplAdProduct {
    companion object {
        val NONE = PcMkplAdProductNone
    }
}

object PcMkplAdProductNone: IPcMkplAdProduct
