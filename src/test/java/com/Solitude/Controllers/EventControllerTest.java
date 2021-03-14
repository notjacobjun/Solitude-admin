package com.Solitude.Controllers;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;
import com.Solitude.controllers.EventController;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// we use random port to avoid conflicts in test environments
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
// need this to enable mockito annotations in Junit 5
@ExtendWith(MockitoExtension.class)
// Integration tests for the Event Controller
        // make sure to focus on the HTTP response code
        // other HTTP headers in the response
        // the payload (JSON)
class EventControllerTest {
    // TODO add a before advice so that we add the test event in the DB

    private EventController eventController;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    void initTestCase() {
        eventController = new EventController(eventRepository, locationRepository);
    }

    @LocalServerPort
    private int port;

    // spring boot provides the TestREST template, we just need to autowire it
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
        // Ensuring that the response contains JSON data
    void
    givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson()
            throws IOException {

        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet(System.getenv("DB_URL") + System.getenv("DB_PORT") + "/location/123/events/");

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }

//    @Test
//    void mockEventController_getEventById_expectedTestEvent() throws Exception {
//        // uses the test event and location in the database
//        Long testLocationId = 123456789L;
//        eventController.getEventByLocationId(testLocationId,)
//    }

    @Test
    void mockEventController_httpStatus_ExpectStatus200() throws Exception {
        mockMvc.perform(get("/location/123/events/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void addBookingEvent() {
    }

    @Test
    void updateBookingEvent() {
    }

    @Test
    void deleteEvent() {
    }
}