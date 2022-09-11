package ru.gorbunovas.pcmarketplace.common.repo

import ru.gorbunovas.pcmarketplace.common.models.*

data class  DbAdIdRequest(
    val id: PcMkplAdId,
    val lock: PcMkplAdLock = PcMkplAdLock.NONE,
) {
    constructor(ad: PcMkplAd): this(ad.id, ad.lock)
}
