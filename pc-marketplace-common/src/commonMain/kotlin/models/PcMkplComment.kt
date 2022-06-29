package ru.gorbunovas.pcmarketplace.common.models

import ru.gorbunovas.pcmarketplace.common.NONE
import kotlinx.datetime.Instant

data class PcMkplComment(
    var id: PcMkplAdCommentId = PcMkplAdCommentId.NONE,
    var title: String = "",
    var text: String = "",
    var buyerId: PcMkplUserId = PcMkplUserId.NONE,
    var createDate: Instant = Instant.NONE
) {
}
