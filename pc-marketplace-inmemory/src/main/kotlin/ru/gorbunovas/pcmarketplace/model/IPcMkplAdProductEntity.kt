package ru.gorbunovas.pcmarketplace.model

import ru.gorbunovas.pcmarketplace.common.models.product.*

sealed interface IPcMkplAdProductEntity


class PcMkplAdProductPcCpuEntity(
    val core: Int? = null,
    val clock: Int? = null,
    val model: String? = null
) {
    constructor(model: PcMkplAdProductPcCpu): this(
        core = model.core,
        clock = model.clock,
        model = model.model
    )

    fun toInternal() = PcMkplAdProductPcCpu(
        core = core?: 0,
        clock = clock?: 0,
        model = model?: ""
    )
}

data class PcMkplAdProductPcRamEntity(
    val typeRam: String? = null,
    var clock: Int? = null,
    var model: String? = null
) {
    constructor(model: PcMkplAdProductPcRam): this(
        typeRam = model.typeRam.takeIf { it != PcMkplAdProductPcRam.TypeRam.NONE }?.name,
        clock = model.clock,
        model = model.model
    )

    fun toInternal() = PcMkplAdProductPcRam(
        typeRam = typeRam?.let { PcMkplAdProductPcRam.TypeRam.valueOf(it) }?: PcMkplAdProductPcRam.TypeRam.NONE ,
        clock = clock?: 0,
        model = model?: ""
    )
}

/*
product = PcMkplAdProductPc(
                price = 50000,
                hdd = "ssd 1Tb",
                motherboard = "s360",
                formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
                type = "server",
                cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 2000),
                ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
 */
data class PcMkplAdProductEntity(
    val price: Double? = null,
    val hdd: String? = null,
    val motherboard: String? = null,
    val formFactor: String? = null,
    val type: String? = null,
    val cpu: PcMkplAdProductPcCpuEntity? = null,
    val ram: PcMkplAdProductPcRamEntity? = null
): IPcMkplAdProductEntity {
    constructor(model: PcMkplAdProductPc): this(
        price = model.price,
        hdd = model.hdd,
        motherboard = model.motherboard,
        formFactor = model.formFactor.takeIf { it != PcMkplAdProductPcFormfactor.NONE }?.name,
        type = model.type,
        cpu = model.cpu.takeIf { it != PcMkplAdProductPcCpu.NONE }?.let { PcMkplAdProductPcCpuEntity(it) },
        ram = model.ram.takeIf { it != PcMkplAdProductPcRam.NONE }?.let { PcMkplAdProductPcRamEntity(it) }
    )

    fun toInternal() = PcMkplAdProductPc(
        price = price?: 0.0,
        hdd = hdd?: "",
        motherboard = motherboard?: "",
        formFactor = formFactor?.let { PcMkplAdProductPcFormfactor.valueOf(it) }?: PcMkplAdProductPcFormfactor.NONE,
        type = type?: "",
        cpu = cpu?.toInternal()?: PcMkplAdProductPcCpu.NONE,
        ram = ram?.toInternal()?: PcMkplAdProductPcRam.NONE
    )
}

fun IPcMkplAdProductEntity?.toInternal() = when(this) {
    is PcMkplAdProductEntity -> this.toInternal()
    null -> IPcMkplAdProduct.NONE
}

internal fun IPcMkplAdProduct.toEntity() = when(this) {
    is PcMkplAdProductPc -> PcMkplAdProductEntity(this)
    IPcMkplAdProduct.NONE -> null
    PcMkplAdProductNone -> null
}
