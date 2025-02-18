package br.com.handleservice.util

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class LocalTimeAdapter : JsonDeserializer<LocalTime>, JsonSerializer<LocalTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalTime {
        return OffsetDateTime.parse(json.asString, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalTime()
    }

    override fun serialize(
        src: LocalTime,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src.toString()) // Ou utilize um DateTimeFormatter customizado
    }
}
