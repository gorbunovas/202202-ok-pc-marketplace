import ru.gorbunovas.pcmarketplace.AdRepoInMemory
import ru.gorbunovas.pcmarketplace.RepoAdCreateTest
import ru.gorbunovas.pcmarketplace.common.repo.IAdRepository

class AdRepoInMemoryCreateTest: RepoAdCreateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}