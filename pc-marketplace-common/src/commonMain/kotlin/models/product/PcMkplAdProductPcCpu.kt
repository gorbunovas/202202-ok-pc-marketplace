package ru.gorbunovas.pcmarketplace.common.models.product

data class PcMkplAdProductPcCpu (
    var core: Int = 0,
    var clock: Int = 0,
    var model: String = ""
) {
    companion object {
        val NONE = PcMkplAdProductPcCpu()
    }
}