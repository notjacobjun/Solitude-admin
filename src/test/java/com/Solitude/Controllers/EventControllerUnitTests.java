package com.Solitude.Controllers;

import com.Solitude.Entity.BookingEvent;
import com.Solitude.Repository.EventRepository;
import com.Solitude.Repository.LocationRepository;
import com.Solitude.Service.EventServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// we use random port to avoid conflicts in test environments
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
// need this to enable mockito annotations in Junit 5
@ExtendWith(MockitoExtension.class)
class EventControllerUnitTests {
    // TODO add a before advice so that we add the test event in the DB

    private EventController eventController;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private EventServiceImplementation eventServiceImplementation;

    @BeforeEach
    void initTestCase() {
        eventController = new EventController(eventRepository, locationRepository, eventServiceImplementation);
    }

    @LocalServerPort
    private int port;

    // spring boot provides the TestREST template, we just need to autowire it
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    //    @Before
//    public void setUp() throws Exception {
//    }

    @Test
    void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
            throws ClientProtocolException, IOException {

        // Given
        Random rand = new Random();
        int testEventId = rand.nextInt(1000);
        HttpUriRequest request = new HttpGet(System.getenv("DB_URL") + System.getenv("DB_PORT") + "/events/" + testEventId);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        MatcherAssert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }

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
    void givenLocationIsIgnoredByJackson_whenTestBookingEventIsSerialized_thenCorrect()
            throws IOException {

        ObjectMapper mapper = new ObjectMapper();
//        BookingEvent testBookingEvent = new BookingEvent("test@gmail.com", "testEvent", null,
//                "testing purposes only", "creator@gmail.com", 3, System.currentTimeMillis()
//                , System.currentTimeMillis() + 23, 234, false, false);

        String testEventAsString = mapper.writeValueAsString(new BookingEvent());

        assertNotEquals("location", testEventAsString);
        System.out.println(testEventAsString);
    }

    @Test
    void getEventByIdMapping_whenGivenTestObject_ExpectsTestEmailInfoMatch() throws Exception {
        int existingTestEventId = 123;
        this.mockMvc.perform(get(System.getenv("DB_URL") + System.getenv("DB_PORT") + "/location/" + existingTestEventId + "/events/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creatorEmail", Matchers.is("test@gmail.com")))
                .andExpect(jsonPath("$.locationID", Matchers.is(123)));
    }

    @Test
    void addBookingEventPostMapping_WhenGivenTestEvent_ExpectsCreatorEmailMatch() throws Exception {
        int RandomTestUserId = 432;
        // checking two unique JSON key value pairs
        this.mockMvc.perform(post(System.getenv("DB_URL") + System.getenv("DB_PORT") + "location/" + RandomTestUserId + "/events")).andExpect(status().isOk()).andExpect(jsonPath(".id", is("uniqueTestId")))
                .andExpect(jsonPath("$.start.dateTime", is("1985-04-12T23:20:50.52Z")));
    }

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