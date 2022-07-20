package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*

fun ICorChainDsl<PcMkplContext>.stubs(title: String, block: ICorChainDsl<PcMkplContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == PcMkplWorkMode.STUB && state == PcMkplState.RUNNING }
}
