package ru.gorbunovas.pcmarketplace.common.models

data class PcMkplAdFilter(
    var searchString: String = "",
    var ownerId: PcMkplUserId = PcMkplUserId.NONE,
    var dealSide: PcMkplDealSide = PcMkplDealSide.NONE,
)
