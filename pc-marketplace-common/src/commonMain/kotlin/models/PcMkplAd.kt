package ru.gorbunovas.pcmarketplace.common.models

import ru.gorbunovas.pcmarketplace.common.models.product.IPcMkplAdProduct

data class PcMkplAd(
    var id: PcMkplAdId = PcMkplAdId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: PcMkplUserId = PcMkplUserId.NONE,
    var adType: PcMkplDealSide = PcMkplDealSide.NONE,
    var visibility: PcMkplVisibility = PcMkplVisibility.NONE,
    var product: IPcMkplAdProduct = IPcMkplAdProduct.NONE,
    var lock: PcMkplAdLock = PcMkplAdLock.NONE,
    val permissionsClient: MutableSet<PcMkplAdPermissionClient> = mutableSetOf(),
    var comments: MutableList<PcMkplComment> = mutableListOf()
) {
    fun deepCopy(
    ) = PcMkplAd(
        id = this@PcMkplAd.id,
        title = this@PcMkplAd.title,
        description = this@PcMkplAd.description,
        ownerId = this@PcMkplAd.ownerId,
        adType = this@PcMkplAd.adType,
        visibility = this@PcMkplAd.visibility,
        lock = this@PcMkplAd.lock,
        product = this@PcMkplAd.product.deepCopy(),
        permissionsClient = this@PcMkplAd.permissionsClient.toMutableSet()
    )
}