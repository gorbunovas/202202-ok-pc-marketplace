package ru.gorbunovas.pcmarketplace.biz.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.errorConcurrency
import ru.gorbunovas.pcmarketplace.common.helpers.fail
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState

fun ICorChainDsl<PcMkplContext>.repoCheckReadLock(title: String) = worker {
    this.title = title
    description = """
        Проверяем, что блокировка из запроса совпадает с блокировкой в БД
    """.trimIndent()
    on { state == PcMkplState.RUNNING && adValidated.lock != adRepoRead.lock }
    handle {
        fail(errorConcurrency(violationCode = "changed", "Object has been inconsistently changed"))
        adRepoDone = adRepoRead
    }
}
