package ru.gorbunovas.pcmarketplace.common.models

data class PcMkplError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
    val level: PcMkplErrorLevels = PcMkplErrorLevels.ERROR,
)

enum class PcMkplErrorLevels {
    ERROR,
    INFO,
}
