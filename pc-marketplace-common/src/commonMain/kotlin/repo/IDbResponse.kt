package ru.gorbunovas.pcmarketplace.common.repo

import ru.gorbunovas.pcmarketplace.common.models.PcMkplError

interface IDbResponse<T> {
    val result: T?
    val isSuccess: Boolean
    val errors: List<PcMkplError>
}
