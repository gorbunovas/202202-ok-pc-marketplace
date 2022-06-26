package ru.gorbunovas.pcmarketplace.stubs.ru.gorbunovas.pcmarketplace.stubs

//import PcMkplAdStubsPc.AD_DEMAND_PC1
import ru.gorbunovas.pcmarketplace.common.models.PcMkplAd
import ru.gorbunovas.pcmarketplace.common.models.PcMkplAdId
import ru.gorbunovas.pcmarketplace.common.models.PcMkplDealSide
import ru.gorbunovas.pcmarketplace.stubs.ru.gorbunovas.pcmarketplace.stubs.PcMkplAdStubsPc.AD_DEMAND_PC1

object PcMkplStub {
    fun get(): PcMkplAd = AD_DEMAND_PC1.copy()

    fun prepareResult(block: PcMkplAd.() -> Unit): PcMkplAd = get().apply(block)

    fun prepareSearchList(filter: String, type: PcMkplDealSide) = listOf(
        mkplAdDemand("d-88005553535-01", filter, type),
        mkplAdDemand("d-88005553535-02", filter, type),
        mkplAdDemand("d-88005553535-03", filter, type),
        mkplAdDemand("d-88005553535-04", filter, type),
        mkplAdDemand("d-88005553535-05", filter, type),
        mkplAdDemand("d-88005553535-06", filter, type),
    )

    fun prepareOffersList(filter: String, type: PcMkplDealSide) = listOf(
        mkplAdSupply("s-88005553535-01", filter, type),
        mkplAdSupply("s-88005553535-02", filter, type),
        mkplAdSupply("s-88005553535-03", filter, type),
        mkplAdSupply("s-88005553535-04", filter, type),
        mkplAdSupply("s-88005553535-05", filter, type),
        mkplAdSupply("s-88005553535-06", filter, type),
    )

    private fun mkplAdDemand(id: String, filter: String, type: PcMkplDealSide) =
        mkplAd(AD_DEMAND_PC1, id = id, filter = filter, type = type)

    private fun mkplAdSupply(id: String, filter: String, type: PcMkplDealSide) =
        mkplAd(AD_DEMAND_PC1, id = id, filter = filter, type = type)

    private fun mkplAd(base: PcMkplAd, id: String, filter: String, type: PcMkplDealSide) = base.copy(
        id = PcMkplAdId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        adType = type,
    )
}