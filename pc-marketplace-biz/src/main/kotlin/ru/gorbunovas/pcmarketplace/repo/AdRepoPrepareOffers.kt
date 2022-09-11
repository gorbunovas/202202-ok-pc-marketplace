package ru.gorbunovas.pcmarketplace.biz.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState

fun ICorChainDsl<PcMkplContext>.repoPrepareOffers(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску предложений в БД"
    on { state == PcMkplState.RUNNING }
    handle {
        adRepoPrepare = adRepoRead.deepCopy()
        adRepoDone = adRepoRead.deepCopy()
    }
}
