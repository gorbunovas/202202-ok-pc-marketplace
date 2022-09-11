package ru.gorbunovas.pcmarketplace.common.helpers

import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*

fun Throwable.asMkplError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = PcMkplError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun PcMkplContext.addError(error: PcMkplError) = errors.add(error)
fun PcMkplContext.fail(error: PcMkplError) {
    addError(error)
    state = PcMkplState.FAILING
}

fun errorConcurrency(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: PcMkplErrorLevels = PcMkplErrorLevels.ERROR,
) = PcMkplError(
    field = "lock",
    code = "concurrent-$violationCode",
    group = "concurrency",
    message = "Concurrent object access error: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: PcMkplErrorLevels = PcMkplErrorLevels.ERROR,
) = PcMkplError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)
