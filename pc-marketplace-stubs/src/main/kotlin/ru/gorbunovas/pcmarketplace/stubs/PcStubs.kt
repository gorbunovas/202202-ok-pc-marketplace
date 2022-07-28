package ru.gorbunovas.pcmarketplace.stubs

import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.models.product.*

object PcStubs {
    private fun stubReady() = PcMkplAd(
        title = "title",
        description = "desc",
        adType = PcMkplDealSide.DEMAND,
        visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        product = PcMkplAdProductPc(
            price = 10000,
            hdd = "ssd 1Tb",
            motherboard = "s360",
            formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
            type = "server",
            cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 10),
            ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
        ),
        comments = mutableListOf(PcMkplComment(PcMkplAdCommentId("11111111111"), "Вопрос о продукте", "Пропатчен под freeBSD?"))
    )

    private fun stubInProgress() = PcMkplAd(
        id = PcMkplAdId(id = "012345678"),
        title = "теоритически лучший ПК",
        description = "Игры? Фотошоп? Миникуб с докером? Всё запустится и одновременно!",
        ownerId = PcMkplUserId(id = "11111"),
        visibility = PcMkplVisibility.VISIBLE_TO_OWNER,
        adType = PcMkplDealSide.SUPPLY,
        permissionsClient = mutableSetOf(PcMkplAdPermissionClient.MAKE_VISIBLE_OWNER)
    )

    fun getModel(model: (PcMkplAd.() -> Unit)? = null) = model?.let {
        stubReady().apply(it)
    } ?: stubReady()

    fun getModels() = listOf(
        stubReady(),
        stubInProgress()
    )

    fun PcMkplAd.update(updateableAd: PcMkplAd) = apply {
        title = updateableAd.title
        description = updateableAd.description
        ownerId = updateableAd.ownerId
        visibility = updateableAd.visibility
        permissionsClient.addAll(updateableAd.permissionsClient)
        comments.addAll(updateableAd.comments)
//        product = updateableAd.product as PcMkplAdProductPc
    }
}