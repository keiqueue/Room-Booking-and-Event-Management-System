package ca.gbc.eventservice;

import ca.gbc.eventservice.dto.RoomDTO;
import ca.gbc.eventservice.dto.UserDTO;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import ca.gbc.eventservice.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void shouldSaveEvent() {
        // Mock Event data
        Event event = new Event();
        event.setEventName("Conference");
        event.setOrganizerId("organizer123");
        event.setEventType("Business");
        event.setExpectedAttendees(50);
        event.setRoomId("room123");

        // Mock repository save behavior
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Mock Room availability
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId("room123");
        roomDTO.setAvailable(true);
        when(restTemplate.getForObject(eq("http://room-service:8086/api/rooms/room123"), eq(RoomDTO.class)))
                .thenReturn(roomDTO);

        // Mock User authorization
        UserDTO userDTO = new UserDTO();
        userDTO.setId("organizer123");
        userDTO.setUserType("Staff"); // Authorized user type
        when(restTemplate.getForObject(eq("http://user-service:8088/api/users/organizer123"), eq(UserDTO.class)))
                .thenReturn(userDTO);

        // Call saveEvent and verify
        Event savedEvent = eventService.saveEvent(event);
        assertNotNull(savedEvent, "Saved event should not be null");
        assertEquals("Conference", savedEvent.getEventName(), "Event name should match");
    }
}
