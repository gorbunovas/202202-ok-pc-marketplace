package ru.gorbunovas.pcmarketplace.biz.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState
import ru.gorbunovas.pcmarketplace.common.repo.DbAdRequest

fun ICorChainDsl<PcMkplContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == PcMkplState.RUNNING }
    handle {
        val request = DbAdRequest(
            adRepoPrepare.deepCopy().apply {
                this.title = adValidated.title
                description = adValidated.description
                adType = adValidated.adType
                visibility = adValidated.visibility
            }
        )
        val result = adRepo.updateAd(request)
        val resultAd = result.result
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = PcMkplState.FAILING
            errors.addAll(result.errors)
            adRepoDone
        }
    }
}
