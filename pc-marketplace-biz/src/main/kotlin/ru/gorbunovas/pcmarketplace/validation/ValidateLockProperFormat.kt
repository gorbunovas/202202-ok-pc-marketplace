package ru.gorbunovas.pcmarketplace.validation

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.errorValidation
import ru.gorbunovas.pcmarketplace.common.helpers.fail
import ru.gorbunovas.pcmarketplace.common.models.PcMkplAdLock

fun ICorChainDsl<PcMkplContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в PcMkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.lock != PcMkplAdLock.NONE && ! adValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
