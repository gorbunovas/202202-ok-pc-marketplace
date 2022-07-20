package ru.gorbunovas.pcmarketplace.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState

fun ICorChainDsl<PcMkplContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == PcMkplState.NONE }
    handle { state = PcMkplState.RUNNING }
}
