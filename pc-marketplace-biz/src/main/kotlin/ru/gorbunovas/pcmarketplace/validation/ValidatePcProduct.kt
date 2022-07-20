package ru.gorbunovas.pcmarketplace.validation

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.errorValidation
import ru.gorbunovas.pcmarketplace.common.helpers.fail
import ru.gorbunovas.pcmarketplace.common.models.product.*

fun ICorChainDsl<PcMkplContext>.validateNotEmptyProductCpu(title: String) = worker {
    this.title = title
    on {
        if(adValidating.product == PcMkplAdProductNone) return@on false
        val product = adValidating.product as PcMkplAdProductPc
        if(product.cpu != PcMkplAdProductPcCpu.NONE
            && product.cpu.model.isNotEmpty()) return@on false
        return@on true;
    }
    handle {
        fail(
            errorValidation(
            field = "CPU",
            violationCode = "noContent",
            description = "field must not empty"
        )
        )
    }
}


fun ICorChainDsl<PcMkplContext>.validateNotEmptyProductRam(title: String) = worker {
    this.title = title
    on {
        if(adValidating.product == PcMkplAdProductNone) return@on false
        val product = adValidating.product as PcMkplAdProductPc
        if(product.ram != PcMkplAdProductPcRam.NONE
            && product.ram.model.isNotEmpty()) return@on false
        return@on true;
    }
    handle {
        fail(
            errorValidation(
                field = "RAM",
                violationCode = "noContent",
                description = "field must not empty"
            )
        )
    }
}


fun ICorChainDsl<PcMkplContext>.validateNotEmptyProductMotherboard(title: String) = worker {
    this.title = title
    on {
        if(adValidating.product == PcMkplAdProductNone) return@on false
        val product = adValidating.product as PcMkplAdProductPc
        if(product.motherboard.isNotEmpty()) return@on false
        return@on true;
    }
    handle {
        fail(
            errorValidation(
                field = "motherboard",
                violationCode = "noContent",
                description = "field must not empty"
            )
        )
    }
}

