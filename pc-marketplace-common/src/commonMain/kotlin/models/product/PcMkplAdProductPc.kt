package ru.gorbunovas.pcmarketplace.common.models.product

data class PcMkplAdProductPc (
    var type: String = "",
    var price: Int = 0,
    var formFactor: PcMkplAdProductPcFormfactor = PcMkplAdProductPcFormfactor.NONE,
    var motherboard: String = "",
    var hdd: String = "",
    var cpu: PcMkplAdProductPcCpu = PcMkplAdProductPcCpu.NONE,
    var ram: PcMkplAdProductPcRam = PcMkplAdProductPcRam.NONE
): IPcMkplAdProduct

enum class PcMkplAdProductPcFormfactor {
    FULL_TOWER,
    MINI_TOWER,
    MIDI_TOWER,
    SFF,
    NONE;
}
