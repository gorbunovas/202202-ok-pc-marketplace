package ru.gorbunovas.pcmarketplace.common.helpers

import ru.gorbunovas.pcmarketplace.common.models.PcMkplError
import ru.gorbunovas.pcmarketplace.common.models.PcMkplErrorLevels

fun errorMapping(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: PcMkplErrorLevels = PcMkplErrorLevels.ERROR,
) = PcMkplError(
    code = "mapping-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)
