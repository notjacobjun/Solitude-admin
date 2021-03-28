package com.Solitude.RESTHelper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.api.services.calendar.model.Event;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class GoogleCalendarEventCombinedSerializer {
    // TODO figure out how to use this serializer within the serializers that is automatically used in the request body annotation
    public static class EventJsonSerializer
            extends JsonSerializer<Event> {

        @Override
        public void serialize(Event event, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", event.getId());
            jsonGenerator.writeStringField("summary", event.getSummary());
            jsonGenerator.writeStringField("description", event.getDescription());
//            jsonGenerator.writeStringField("location", event.getLocation());
            // check if this is valid for writing into the json object 'creator'
            jsonGenerator.writeStringField("creator.email", event.getCreator().getEmail());
            jsonGenerator.writeNumberField("attendees[].additionalGuests", event.getAttendees().size());
            jsonGenerator.writeStringField("start.dateTime", event.getStart().getDateTime().toString());
            jsonGenerator.writeStringField("end.dateTime", event.getEnd().getDateTime().toString());
            jsonGenerator.writeEndObject();
        }
    }

    public static class EventJsonDeserializer
            extends JsonDeserializer<Event> {

        @Override
        public Event deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext)
                throws IOException {

            TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
            TextNode id = (TextNode) treeNode.get(
                    "id");
            // TODO finish this contructor with all required fields
            return new Event();
        }
    }
}
