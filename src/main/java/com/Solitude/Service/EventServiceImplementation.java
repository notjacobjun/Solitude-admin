package com.Solitude.Service;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.google.api.services.calendar.model.Event;
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
        return new Event()
                .setId(event.getEventId())
                .setSummary(event.getEventName())
                .setDescription(event.getDescription());
    }

    // TODO create update fields for more detailed contruction of the GC event
}
