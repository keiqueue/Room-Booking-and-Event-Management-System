package ca.gbc.bookingservice;

import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import ca.gbc.bookingservice.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveBooking() {
        Booking booking = new Booking();
        booking.setUserId("user123");
        booking.setRoomId("room123");
        booking.setStartTime(LocalDateTime.of(2024, 1, 1, 10, 0));
        booking.setEndTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        booking.setPurpose("Meeting");

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.saveBooking(booking);
        assertNotNull(savedBooking);
        assertEquals("user123", savedBooking.getUserId());
        assertEquals("room123", savedBooking.getRoomId());
        assertEquals("Meeting", savedBooking.getPurpose());
    }

    @Test
    void shouldGetBookingById() {
        Booking booking = new Booking();
        booking.setUserId("user123");
        booking.setRoomId("room123");

        when(bookingRepository.findById("1")).thenReturn(Optional.of(booking));

        Optional<Booking> foundBooking = bookingService.getBookingById("1");
        assertNotNull(foundBooking);
        assertEquals("user123", foundBooking.get().getUserId());
        assertEquals("room123", foundBooking.get().getRoomId());
    }

    @Test
    void shouldGetAllBookings() {
        Booking booking1 = new Booking();
        booking1.setUserId("user123");
        booking1.setRoomId("room123");

        Booking booking2 = new Booking();
        booking2.setUserId("user456");
        booking2.setRoomId("room456");

        when(bookingRepository.findAll()).thenReturn(List.of(booking1, booking2));

        List<Booking> bookings = bookingService.getAllBookings();
        assertNotNull(bookings);
        assertEquals(2, bookings.size());
        assertEquals("user123", bookings.get(0).getUserId());
        assertEquals("user456", bookings.get(1).getUserId());
    }
}
