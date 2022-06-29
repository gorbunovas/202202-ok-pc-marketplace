package exceptions

import ru.gorbunovas.pcmarketplace.api.v1.models.IAdProduct

class UnknownAdProduct(prod: IAdProduct) : Throwable("Cannot map unknown product $prod") {

}
