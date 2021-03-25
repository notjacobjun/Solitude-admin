package com.Solitude.RESTHelper;

import com.Solitude.Entity.Location;
import lombok.Data;

@Data
public class BookingEventDTO {
    private String eventId;
    private String eventName;
    private Location location;
    private String description;
    private String creatorEmail;
    private int partyNumber;
    private String startTime;
    private String endTime;
    private Long userID;
    private boolean checkedIn;
    private boolean checkedOut;
}
