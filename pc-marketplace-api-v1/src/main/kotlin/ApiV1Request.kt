package ru.gorbunovas.pcmarketplace.api.v1

import ru.gorbunovas.pcmarketplace.api.v1.models.IRequest

fun apiV1RequestSerialize(request: IRequest): String = jacksonMapper.writeValueAsString(request)

fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    jacksonMapper.readValue(json, IRequest::class.java) as T
