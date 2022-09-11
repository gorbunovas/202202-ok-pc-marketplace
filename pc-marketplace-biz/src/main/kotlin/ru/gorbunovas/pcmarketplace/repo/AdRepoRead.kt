package ru.gorbunovas.pcmarketplace.biz.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.*
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState
import ru.gorbunovas.pcmarketplace.common.repo.DbAdIdRequest


fun ICorChainDsl<PcMkplContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == PcMkplState.RUNNING }
    handle {
        val request = DbAdIdRequest(adValidated)
        val result = adRepo.readAd(request)
        val resultAd = result.result
        if (result.isSuccess && resultAd != null) {
            adRepoRead = resultAd
        } else {
            state = PcMkplState.FAILING
            errors.addAll(result.errors)
        }
    }
}
