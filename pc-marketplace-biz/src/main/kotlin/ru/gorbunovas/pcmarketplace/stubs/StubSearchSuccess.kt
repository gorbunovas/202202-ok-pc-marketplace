package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplDealSide
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs


fun ICorChainDsl<PcMkplContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == PcMkplStubs.SUCCESS && state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FINISHING
        adsResponse.addAll(PcMkplStub.prepareSearchList(adFilterRequest.searchString, PcMkplDealSide.DEMAND))
    }
}
