package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs

fun ICorChainDsl<PcMkplContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == PcMkplStubs.DB_ERROR && state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FAILING
        this.errors.add(
            PcMkplError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
