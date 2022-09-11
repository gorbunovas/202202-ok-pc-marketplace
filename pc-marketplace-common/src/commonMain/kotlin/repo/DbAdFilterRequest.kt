package ru.gorbunovas.pcmarketplace.common.repo

import ru.gorbunovas.pcmarketplace.common.models.*

data class DbAdFilterRequest (
    val titleFilter: String = "",
    val ownerId: PcMkplUserId = PcMkplUserId.NONE,
    val dealSide: PcMkplDealSide = PcMkplDealSide.NONE,
    val searchTypes: Set<PcMkplSearchTypes> = setOf(),
)