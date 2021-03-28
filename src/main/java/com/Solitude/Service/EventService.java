package com.Solitude.Service;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.google.api.services.calendar.model.Event;

public interface EventService {
    BookingEventDTO convertToDTO(BookingEvent event);
    BookingEvent convertToEntity(BookingEventDTO eventDTO);

    Event convertToGCEvent(BookingEvent event);
}
