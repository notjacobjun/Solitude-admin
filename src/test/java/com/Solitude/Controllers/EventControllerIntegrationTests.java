package com.Solitude.Controllers;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@WebMvcTest(EventController.class)
@ExtendWith(MockitoExtension.class)
// Integration tests for the Event Controller
// make sure to focus on the HTTP response code
// other HTTP headers in the response
// the payload (JSON)
class EventControllerIntegrationTests {
    @LocalServerPort
    private int port;

    // spring boot provides the TestREST template, we just need to autowire it
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private LocationRepository locationRepository;


    @Test
    void addBookingEventPostMapping_WhenGivenTestEvent_ExpectsCreatorEmailMatch() throws Exception {
        int RandomTestUserId = 432;
        String testEventData = "{\n" +
                "    \"id\" : \"uniqueTestId\",\n" +
                "    \"status\" : \"tentative\",\n" +
                "    \"summary\" : \"testing purposes\",\n" +
                "    \"description\" : \"arbitrary test event\",\n" +
                "    \"start\" : {\n" +
                "        \"dateTime\" : \"1985-04-12T23:20:50.52Z\"\n" +
                "    },\n" +
                "    \"end\" : {\n" +
                "        \"dateTime\" : \"1985-04-12T23:22:50.52Z\"\n" +
                "    },\n" +
                "    \"creator\" : {\n" +
                "        \"id\" : \"testCreator\",\n" +
                "        \"email\" : \"test@gmail.com\"\n" +
                "    },\n" +
                "    \"attendees\" : [\n" +
                "        {\n" +
                "            \"id\": \"testUser1\",\n" +
                "            \"email\": \"testUser@gmail.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"testUser2\",\n" +
                "            \"email\": \"testUser2@gmail.com\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        BookingEvent testEvent = mapper.readValue(testEventData, BookingEvent.class);
        // TODO figure out why this won't work
//        when(eventRepository.save(any(BookingEvent.class))).thenReturn(testEvent);
        // checking two unique JSON key value pairs
        this.mockMvc.perform(post(System.getenv("DB_URL") + System.getenv("DB_PORT") + "location/" + RandomTestUserId + "/events")).andExpect(status().isOk()).andExpect(jsonPath(".id", is("uniqueTestId")))
                .andExpect(jsonPath("$.start.dateTime", is("1985-04-12T23:20:50.52Z")));
    }
}
