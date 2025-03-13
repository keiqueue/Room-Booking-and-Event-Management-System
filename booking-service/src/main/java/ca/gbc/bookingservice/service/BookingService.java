/**
 * COMP 3095 - Group Assignment
 * @Auhtors: Jam Furaque, Andrew Stewart, Kei Ishikawa, Carl Trinidad
 * */
package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ca.gbc.bookingservice.dto.RoomDTO;




import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Booking saveBooking(Booking booking) {
        if (!isRoomAvailable(booking.getRoomId())) {
            throw new RuntimeException("Room is not available.");
        }
        if (isDoubleBooking(booking)) {
            throw new RuntimeException("This room is already booked for the specified time range.");
        }
        return bookingRepository.save(booking);
    }

    public List<RoomDTO> getAvailableRooms() {
        String url = "http://room-service:8086/api/rooms/available";
        try {
            RoomDTO[] availableRooms = restTemplate.getForObject(url, RoomDTO[].class);
            return List.of(availableRooms);
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve available rooms: " + e.getMessage());
        }
    }

    private boolean isRoomAvailable(String roomId) {
        String url = "http://room-service:8086/api/rooms/" + roomId; // Assuming the correct URL for room availability
        RoomDTO room = restTemplate.getForObject(url, RoomDTO.class);
        return room != null && room.isAvailable();
    }

    private boolean isDoubleBooking(Booking booking) {
        List<Booking> existingBookings = bookingRepository.findByRoomId(booking.getRoomId());
        for (Booking existingBooking : existingBookings) {
            if (booking.getStartTime().isBefore(existingBooking.getEndTime()) &&
                    booking.getEndTime().isAfter(existingBooking.getStartTime())) {
                return true; // Overlap detected
            }
        }
        return false;
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(String id){
        return bookingRepository.findById(id);
    }

    public Booking updateBooking(String id, Booking bookingDetails){
        Booking booking = bookingRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Booking with id " + id + " not found"));
        booking.setUserId(bookingDetails.getUserId());
        booking.setRoomId(bookingDetails.getRoomId());
        booking.setStartTime(bookingDetails.getStartTime());
        booking.setEndTime(bookingDetails.getEndTime());
        booking.setPurpose(bookingDetails.getPurpose());
        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id){
        bookingRepository.deleteById(id);
    }
}
