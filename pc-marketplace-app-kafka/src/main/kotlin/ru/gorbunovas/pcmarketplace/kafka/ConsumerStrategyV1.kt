package ru.gorbunovas.pcmarketplace.kafka

import ru.gorbunovas.mappers.*
import ru.gorbunovas.pcmarketplace.api.v1.*
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import ru.gorbunovas.pcmarketplace.common.PcMkplContext


class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: PcMkplContext): String {
        val response: IResponse = source.toTransportAd()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: PcMkplContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}