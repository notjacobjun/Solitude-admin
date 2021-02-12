package com.Solitude.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleCalendar {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleCalendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    public static String getCalendarId() {
    	String calendarId = null;
		try {
	    	Calendar service = getService();
	
	        CalendarList calendars = service.calendarList().list().execute();
	        List<CalendarListEntry> calendarItems = calendars.getItems();
	        
	        for(CalendarListEntry cal : calendarItems)  {
	        	/* Get the calendarId for the calendar "Solitude"
	        	 * There's probably a better way to do this
	        	 * Filtering while fetching maybe (Couldn't find it in the docs)
	        	 */
	        	if(cal.getSummary().equals("Solitude")) {
	        		calendarId = cal.getId();
	        	}
	        }
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return calendarId;
		}
    }
    
    public static Calendar getService() throws IOException, GeneralSecurityException {
    	// Build a new authorized API client service.
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        return service;
    }

    public static List<Event> getUpcomingEventsByLocation(String location, int size) throws IOException, GeneralSecurityException {
        Calendar service = getService();

        String calendarId = getCalendarId();
        if(calendarId!=null) {
        	DateTime now = new DateTime(System.currentTimeMillis());
        	// Get the next 
        	Events events = service.events().list(calendarId)
                    .setMaxResults(size)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            
            // Filter events based on location
            List<Event> filteredEvents = items.stream()
            	    	        .filter(evnt -> 
            	    	          evnt.getLocation().equals(location))
            	    	        .collect(Collectors.toList());
            return filteredEvents;
        }
        // Default return if there is no CalendarId
        return new ArrayList<Event>();
        
    }
    
    public static List<Event> getPastEventsByLocation(String location, Integer size) throws IOException, GeneralSecurityException {
		Calendar service = getService();

        String calendarId = getCalendarId();
        if(calendarId!=null) {
        	DateTime now = new DateTime(System.currentTimeMillis());
        	// Get the next 
        	Events events = service.events().list(calendarId)
                    .setMaxResults(size)
                    .setTimeMax(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            
            // Filter events based on location
            List<Event> filteredEvents = items.stream()
            	    	        .filter(evnt -> 
            	    	          evnt.getLocation().equals(location))
            	    	        .collect(Collectors.toList());
            return filteredEvents;
        }
        // Default return if there is no CalendarId
        return new ArrayList<Event>();
	}

}
