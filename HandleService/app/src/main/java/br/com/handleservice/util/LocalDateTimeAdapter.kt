import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalDateTime {
        return LocalDateTime.parse(json.asString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    override fun serialize(
        src: LocalDateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
    }
}
