package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplError
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState


fun ICorChainDsl<PcMkplContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FAILING
        this.errors.add(
            PcMkplError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
