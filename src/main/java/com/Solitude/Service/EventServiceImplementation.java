package com.Solitude.Service;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class EventServiceImplementation implements EventService {

    public final ModelMapper modelMapper = new ModelMapper();

    public BookingEventDTO convertToDTO(BookingEvent event) {
        BookingEventDTO bookingEventDTO = modelMapper.map(event, BookingEventDTO.class);
        bookingEventDTO.setEventId(event.getEventId());
        bookingEventDTO.setEventName(event.getEventName());
        bookingEventDTO.setDescription(event.getDescription());
        bookingEventDTO.setCreatorEmail(event.getCreatorEmail());
        bookingEventDTO.setStartTime(event.getStartTime());
        bookingEventDTO.setEndTime(event.getEndTime());
        bookingEventDTO.setLocation(event.getLocation());
        bookingEventDTO.setPartyNumber(event.getPartyNumber());
        bookingEventDTO.setUserID(event.getUserID());
        bookingEventDTO.setCheckedIn(event.isCheckedIn());
        bookingEventDTO.setCheckedOut(event.isCheckedOut());
        return bookingEventDTO;
    }

    public BookingEvent convertToEntity(BookingEventDTO eventDTO) {
        BookingEvent bookingEvent = modelMapper.map(eventDTO, BookingEvent.class);
        bookingEvent.setEventId(eventDTO.getEventId());
        bookingEvent.setEventName(eventDTO.getEventName());
        bookingEvent.setDescription(eventDTO.getDescription());
        bookingEvent.setCreatorEmail(eventDTO.getCreatorEmail());
        bookingEvent.setStartTime(eventDTO.getStartTime());
        bookingEvent.setEndTime(eventDTO.getEndTime());
        bookingEvent.setLocation(eventDTO.getLocation());
        bookingEvent.setPartyNumber(eventDTO.getPartyNumber());
        bookingEvent.setUserID(eventDTO.getUserID());
        bookingEvent.setCheckedIn(eventDTO.isCheckedIn());
        bookingEvent.setCheckedOut(eventDTO.isCheckedOut());
        return bookingEvent;
    }

    public Event convertToGCEvent(BookingEvent event) {
        try {
            Event GCEvent = new Event()
                    .setLocation(event.getLocation().getLocationName())
                    .setSummary(event.getEventName())
                    .setDescription(event.getDescription());

            DateTime startDateTime = new DateTime(event.getStartTime());
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Los_Angeles");
            GCEvent.setStart(start);

            DateTime endDateTime = new DateTime(event.getEndTime());
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Los_Angeles");
            GCEvent.setEnd(end);

            return GCEvent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}