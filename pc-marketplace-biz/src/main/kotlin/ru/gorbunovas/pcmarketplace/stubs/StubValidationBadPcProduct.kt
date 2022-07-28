package ru.gorbunovas.pcmarketplace.stubs

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplError
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs

fun ICorChainDsl<PcMkplContext>.stubValidationBadPcProduct(title: String) = worker {
    this.title = title
    on { stubCase == PcMkplStubs.BAD_PRODUCT && state == PcMkplState.RUNNING }
    handle {
        state = PcMkplState.FAILING
        this.errors.add(
            PcMkplError(
                group = "validation",
                code = "validation-product",
                field = "description",
                message = "Wrong product field"
            )
        )
    }
}
