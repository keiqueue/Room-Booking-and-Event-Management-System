/**
 * COMP 3095 - Group Assignment
 * @Auhtors: Jam Furaque, Andrew Stewart, Kei Ishikawa, Carl Trinidad
 * */
package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.RoomDTO;
import ca.gbc.eventservice.dto.UserDTO;
import ca.gbc.eventservice.exception.RoomNotAvailableException;
import ca.gbc.eventservice.exception.UserNotAuthorizedException;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RestTemplate restTemplate;

    public EventService(EventRepository eventRepository, RestTemplate restTemplate) {
        this.eventRepository = eventRepository;
        this.restTemplate = restTemplate;
    }

    // Method to save an event with validation
    public Event saveEvent(Event event) {
        if (!isRoomAvailable(event.getRoomId())) {
            throw new RoomNotAvailableException("Room is not available for booking.");
        }
        if (!isOrganizerAuthorized(event.getOrganizerId())) {
            throw new UserNotAuthorizedException("Only teachers or staff can book events.");
        }
        return eventRepository.save(event);
    }

    // Method to check if a room is available
    private boolean isRoomAvailable(String roomId) {
        String url = "http://room-service:8086/api/rooms/" + roomId;
        RoomDTO room = restTemplate.getForObject(url, RoomDTO.class);
        return room != null && room.isAvailable();
    }

    // Method to check if the organizer is authorized
    private boolean isOrganizerAuthorized(String organizerId) {
        String url = "http://user-service:8088/api/users/" + organizerId;
        UserDTO user = restTemplate.getForObject(url, UserDTO.class);
        return user != null && (user.getUserType().equalsIgnoreCase("Faculty") || user.getUserType().equalsIgnoreCase("Staff"));
    }

    // Method to retrieve all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Method to retrieve an event by ID
    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    // Method to update an existing event
    public Event updateEvent(String id, Event eventDetails) {
        Event event = eventRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Event not found"));
        event.setEventName(eventDetails.getEventName());
        event.setOrganizerId(eventDetails.getOrganizerId());
        event.setEventType(eventDetails.getEventType());
        event.setExpectedAttendees(eventDetails.getExpectedAttendees());
        event.setRoomId(eventDetails.getRoomId());
        return eventRepository.save(event);
    }

    // Method to delete an event by ID
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}
