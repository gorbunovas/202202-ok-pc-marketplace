package ru.gorbunovas.pcmarketplace.stubs

import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPc
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcCpu
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcFormfactor
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcRam

object PcMkplAdStubsPc {
    val AD_DEMAND_PC1: PcMkplAd
        get() = PcMkplAd(
            id = PcMkplAdId("88005553535"),
            title = "Ищем офисный ПК",
            description = "Требуется 100500 ПК в офис",
            ownerId = PcMkplUserId("user1"),
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
            product = PcMkplAdProductPc(
                price = 50000,
                hdd = "ssd 1Tb",
                motherboard = "s360",
                formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
                type = "server",
                cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 2000),
                ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
            ),
            permissionsClient = mutableSetOf(
                PcMkplAdPermissionClient.READ,
                PcMkplAdPermissionClient.UPDATE,
                PcMkplAdPermissionClient.DELETE,
                PcMkplAdPermissionClient.MAKE_VISIBLE_PUBLIC,
                PcMkplAdPermissionClient.MAKE_VISIBLE_GROUP,
                PcMkplAdPermissionClient.MAKE_VISIBLE_OWNER,
            ),
            comments = mutableListOf(PcMkplComment(PcMkplAdCommentId("22222222"), "Доставка", "Доставка в Ростов"))
        )
    val AD_SUPPLY_PC1 = AD_DEMAND_PC1.copy(adType = PcMkplDealSide.SUPPLY)
}