package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.*


fun ICorChainDsl<PcMkplContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == PcMkplStubs.SUCCESS && state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FINISHING
        val stub = PcMkplStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            adRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            adRequest.adType.takeIf { it != PcMkplDealSide.NONE }?.also { this.adType = it }
            adRequest.visibility.takeIf { it != PcMkplVisibility.NONE }?.also { this.visibility = it }
        }
        adResponse = stub
    }
}
