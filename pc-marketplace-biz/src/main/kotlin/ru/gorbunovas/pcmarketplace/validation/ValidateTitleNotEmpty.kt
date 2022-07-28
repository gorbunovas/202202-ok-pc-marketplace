package ru.gorbunovas.pcmarketplace.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.*


fun ICorChainDsl<PcMkplContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.title.isEmpty() }
    handle {
        fail(errorValidation(
            field = "title",
            violationCode = "empty",
            description = "field must not be empty"
        ))
    }
}
