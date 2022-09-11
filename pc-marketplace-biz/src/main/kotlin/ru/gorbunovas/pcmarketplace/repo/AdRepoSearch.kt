package ru.gorbunovas.pcmarketplace.biz.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState
import ru.gorbunovas.pcmarketplace.common.repo.DbAdFilterRequest

fun ICorChainDsl<PcMkplContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == PcMkplState.RUNNING }
    handle {
        val request = DbAdFilterRequest(
            titleFilter = adFilterValidated.searchString,
            ownerId = adFilterValidated.ownerId,
            dealSide = adFilterValidated.dealSide,
            searchTypes = adFilterValidated.searchTypes.toSet(),
        )
        val result = adRepo.searchAd(request)
        val resultAds = result.result
        if (result.isSuccess && resultAds != null) {
            adsRepoDone = resultAds.toMutableList()
        } else {
            state = PcMkplState.FAILING
            errors.addAll(result.errors)
        }
    }
}
