package ru.gorbunovas.pcmarketplace

import ru.gorbunovas.pcmarketplace.common.models.*

abstract class BaseInitAds(val op: String): IInitObjects<PcMkplAd> {

    fun createInitTestModel(
        suf: String,
        ownerId: PcMkplUserId = PcMkplUserId("owner-123"),
        adType: PcMkplDealSide = PcMkplDealSide.DEMAND,
    ) = PcMkplAd(
        id = PcMkplAdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = PcMkplVisibility.VISIBLE_TO_OWNER,
        adType = adType,
        lock = PcMkplAdLock("20000000-0000-0000-0000-000000000000")
    )
}
