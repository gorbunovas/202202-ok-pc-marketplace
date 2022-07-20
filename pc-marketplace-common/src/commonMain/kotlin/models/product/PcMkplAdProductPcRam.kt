package ru.gorbunovas.pcmarketplace.common.models.product

data class PcMkplAdProductPcRam (
    var typeRam: TypeRam = TypeRam.NONE,
    var clock: Int = 0,
    var model: String = ""
){
    enum class TypeRam {
        DDR3,
        DDR4,
        DDR5,
        DDR6,
        NONE;
    }
    companion object {
        val NONE = PcMkplAdProductPcRam()
    }
}