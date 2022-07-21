package ru.gorbunovas.pcmarketplace.stubs

//import com.fasterxml.jackson.databind.*
//import io.ktor.client.call.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import io.ktor.serialization.jackson.*
//import io.ktor.server.plugins.contentnegotiation.*
//import io.ktor.server.testing.*
//import org.junit.Test
import ru.gorbunovas.pcmarketplace.api.v1.models.*
//import kotlin.test.*

import com.fasterxml.jackson.databind.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.*


class StubTest {

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }

    @Test
    fun create() = testApplication {
//        application {
//            dispose()
//            module()
//        }
//        environment {
//            config = MapApplicationConfig("ktor.deployment.port" to "8080")
//        }
        val client = myClient()

        val response = client.post("/v1/ad/create") {
            val prod = AdProductPC("Office",
                50000,
                AdProductPC.Formfactor.MINI_TOWER,
                "Asus",
                "500Gb",
                AdProductPCcpu(4, 3000),
                AdProductPCram(AdProductPCram.Type.DDR6, 1333)
            )
            val requestObj = AdCreateRequest(
                requestId = "1234567890",
                ad = AdCreateObject(
                    title = "Пк",
                    description = "Сервер",
                    adType = DealSide.DEMAND,
                    visibility = AdVisibility.PUBLIC,
                    product = prod
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("1234567890", responseObj.requestId)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/read") {
            val requestObj = AdReadRequest(
                requestId = "1234567890",
                ad = BaseAdIdRequestAd("0987654321"),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("0987654321", responseObj.ad?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()
        val prod = AdProductPC("Office",
            50000,
            AdProductPC.Formfactor.MINI_TOWER,
            "Asus",
            "500Gb",
            AdProductPCcpu(4, 3000),
            AdProductPCram(AdProductPCram.Type.DDR6, 1333)
        )
        val response = client.post("/v1/ad/update") {
            val requestObj = AdUpdateRequest(
                requestId = "1234567890",
                ad = AdUpdateObject(
                    id = "0987654321",
                    title = "ПК",
                    description = "Офисный ПК",
                    adType = DealSide.DEMAND,
                    visibility = AdVisibility.PUBLIC,
                    product = prod
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("0987654321", responseObj.ad?.id)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/delete") {
            val requestObj = AdDeleteRequest(
                requestId = "1234567890",
                ad = BaseAdIdRequestAd(
                    id = "0987654321",
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("0987654321", responseObj.ad?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/search") {
            val requestObj = AdSearchRequest(
                requestId = "1234567890",
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("title", responseObj.ads?.first()?.title)
    }

    @Test
    fun offers() = testApplication {
        val client = myClient()

        val response = client.post("/v1/ad/offers") {
            val requestObj = AdOffersRequest(
                requestId = "1234567890",
                ad =
                    BaseAdIdRequestAd(
                    id = "0987654321"
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdOffersResponse>()
        assertEquals(200, response.status.value)
        assertEquals("1234567890", responseObj.requestId)
        assertEquals("0987654321", responseObj.offers?.first()?.id)
    }
}