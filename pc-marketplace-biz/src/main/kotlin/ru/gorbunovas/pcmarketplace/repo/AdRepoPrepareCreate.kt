package ru.gorbunovas.pcmarketplace.biz.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState


fun ICorChainDsl<PcMkplContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == PcMkplState.RUNNING }
    handle {
        adRepoPrepare = adValidated.deepCopy()
    }
}
