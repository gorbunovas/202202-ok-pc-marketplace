package ru.gorbunovas.pcmarketplace.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.*
import ru.gorbunovas.pcmarketplace.common.models.PcMkplAdId


fun ICorChainDsl<PcMkplContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.id != PcMkplAdId.NONE && ! adValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(errorValidation(
            field = "id",
            violationCode = "badFormat",
            description = "value $encodedId must contain only"
        ))
    }
}
