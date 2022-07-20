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

