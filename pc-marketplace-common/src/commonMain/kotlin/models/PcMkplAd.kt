package ru.gorbunovas.pcmarketplace.common.models

import ru.gorbunovas.pcmarketplace.common.models.product.IPcMkplAdProduct

data class PcMkplAd(
    var id: PcMkplAdId = PcMkplAdId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: PcMkplUserId = PcMkplUserId.NONE,
    val adType: PcMkplDealSide = PcMkplDealSide.NONE,
    var visibility: PcMkplVisibility = PcMkplVisibility.NONE,
    var product: IPcMkplAdProduct = IPcMkplAdProduct.NONE,
    val permissionsClient: MutableSet<PcMkplAdPermissionClient> = mutableSetOf(),
    var comments: MutableList<PcMkplComment> = mutableListOf()
)
