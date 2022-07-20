package ru.gorbunovas.pcmarketplace.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*

fun ICorChainDsl<PcMkplContext>.operation(title: String, command: PcMkplCommand, block: ICorChainDsl<PcMkplContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == PcMkplState.RUNNING }
}
