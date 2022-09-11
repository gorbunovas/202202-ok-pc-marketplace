package ru.gorbunovas.pcmarketplace.common.models

import ru.gorbunovas.pcmarketplace.common.repo.IAdRepository

data class PcMkplSettings(
    val repoStub: IAdRepository = IAdRepository.NONE,
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
)
