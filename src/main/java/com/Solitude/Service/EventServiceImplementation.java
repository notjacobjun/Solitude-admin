package com.Solitude.Service;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

    public Event updateFields(Event event, String creatorEmail, int partyNumber, String eventStartTime, String eventEndTime) {
        Event.Creator creator = new Event.Creator();
        creator.setEmail(creatorEmail);
        EventAttendee attendees = new EventAttendee();
        attendees.setEmail(creator.getEmail());
        // party number includes the creator so we have to subtract 1 from the count
        attendees.setAdditionalGuests(partyNumber-1);
        DateTime startTime;
        DateTime endTime;
//        Date startDate;
//        Date endDate;
        EventDateTime start = new EventDateTime();
        EventDateTime end = new EventDateTime();
        String timeZone = "America/Los_Angeles";
        try {
            startTime = DateTime.parseRfc3339(eventStartTime);
            endTime = DateTime.parseRfc3339(eventEndTime);
            start = new EventDateTime()
                    .setDateTime(startTime)
                    .setTimeZone(timeZone);
            end = new EventDateTime()
                    .setDateTime(endTime)
                    .setTimeZone(timeZone);
        }
        catch(NumberFormatException e) {
            try {
//                byte[] startData = eventStartTime.getBytes();
//                byte[] endData = eventEndTime.getBytes();
                startTime = DateTime.parseRfc3339(eventStartTime);
                endTime = DateTime.parseRfc3339(eventEndTime);
//                startDate = Date.parseFrom(startData);
//                endDate = Date.parseFrom(endData);
                start = new EventDateTime()
                        .setDate(startTime)
                        .setTimeZone(timeZone);
                end = new EventDateTime()
                        .setDate(endTime)
                        .setTimeZone(timeZone);
            }
            catch (NumberFormatException exception) {
                System.out.println("Wrong date format for updating/initializing Google calendar event fields");
                exception.printStackTrace();
            }
        }
        event.setCreator(creator);
        event.setAttendees(Collections.singletonList(attendees));
        event.setStart(start);
        event.setEnd(end);
        return event;
    }
}