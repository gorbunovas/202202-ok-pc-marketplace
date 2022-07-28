package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplError
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs


fun ICorChainDsl<PcMkplContext>.stubValidationBadTitle(title: String) = worker {
    this.title = title
    on { stubCase == PcMkplStubs.BAD_TITLE && state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FAILING
        this.errors.add(
            PcMkplError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }
}
