package ru.gorbunovas.pcmarketplace.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.errorValidation
import ru.gorbunovas.pcmarketplace.common.helpers.fail


fun ICorChainDsl<PcMkplContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { adValidating.description.isNotEmpty() && ! adValidating.description.contains(regExp) }
    handle {
        fail(errorValidation(
            field = "description",
            violationCode = "noContent",
            description = "field must contain leters"
        ))
    }
}
