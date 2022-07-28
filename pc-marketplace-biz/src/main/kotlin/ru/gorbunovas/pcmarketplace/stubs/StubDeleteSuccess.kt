package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs

fun ICorChainDsl<PcMkplContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == PcMkplStubs.SUCCESS && state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FINISHING
        val stub = PcMkplStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        adResponse = stub
    }
}
