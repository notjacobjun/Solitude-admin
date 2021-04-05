package com.Solitude.Service;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.Entity.Location;
import com.Solitude.RESTHelper.BookingEventDTO;
import com.Solitude.RESTHelper.LocationDTO;

public interface LocationService {
    LocationDTO convertToDTO(Location location);
    Location convertToEntity(LocationDTO locationDTO);
}
