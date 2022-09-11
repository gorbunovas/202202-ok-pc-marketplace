package ru.gorbunovas.pcmarketplace.model

import ru.gorbunovas.pcmarketplace.common.models.*


data class AdEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val adType: String? = null,
    val visibility: String? = null,
    val product: IPcMkplAdProductEntity? = null,
    val lock: String? = null,
) {
    constructor(model: PcMkplAd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        adType = model.adType.takeIf { it != PcMkplDealSide.NONE }?.name,
        visibility = model.visibility.takeIf { it != PcMkplVisibility.NONE }?.name,
        product = model.product.toEntity(),
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = PcMkplAd(
        id = id?.let { PcMkplAdId(it) }?: PcMkplAdId.NONE,
        title = title?: "",
        description = description?: "",
        ownerId = ownerId?.let { PcMkplUserId(it) }?: PcMkplUserId.NONE,
        adType = adType?.let { PcMkplDealSide.valueOf(it) }?: PcMkplDealSide.NONE,
        visibility = visibility?.let { PcMkplVisibility.valueOf(it) }?: PcMkplVisibility.NONE,
        product = product.toInternal(),
        lock = lock?.let { PcMkplAdLock(it) } ?: PcMkplAdLock.NONE,
    )
}
