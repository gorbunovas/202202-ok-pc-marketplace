package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.*

fun ICorChainDsl<PcMkplContext>.stubOffersSuccess(title: String) = worker {
    this.title = title
    on { stubCase == PcMkplStubs.SUCCESS && state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FINISHING
        adResponse = PcMkplStub.prepareResult {
            adRequest.id.takeIf { it != PcMkplAdId.NONE }?.also { this.id = it }
        }
        adsResponse.addAll(PcMkplStub.prepareOffersList(adResponse.title, PcMkplDealSide.SUPPLY))
    }
}
