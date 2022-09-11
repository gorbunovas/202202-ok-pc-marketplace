package ru.gorbunovas.pcmarketplace.general

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.errorAdministration
import ru.gorbunovas.pcmarketplace.common.helpers.fail
import ru.gorbunovas.pcmarketplace.common.models.PcMkplWorkMode
import ru.gorbunovas.pcmarketplace.common.repo.IAdRepository

fun ICorChainDsl<PcMkplContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        adRepo = when (workMode) {
            PcMkplWorkMode.TEST -> settings.repoTest
            PcMkplWorkMode.STUB -> IAdRepository.NONE
            else -> settings.repoProd
        }
        if (workMode != PcMkplWorkMode.STUB && adRepo == IAdRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
