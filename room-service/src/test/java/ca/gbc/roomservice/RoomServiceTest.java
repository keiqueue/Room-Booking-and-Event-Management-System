package ca.gbc.roomservice;

import ca.gbc.roomservice.model.Room;
import ca.gbc.roomservice.repository.RoomRepository;
import ca.gbc.roomservice.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveRoom() {
        Room room = new Room();
        room.setRoomName("Conference Room A");
        room.setCapacity(10);
        room.setFeatures("Projector, Whiteboard");
        room.setAvailable(true);

        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room savedRoom = roomService.saveRoom(room);
        assertNotNull(savedRoom);
        assertEquals("Conference Room A", savedRoom.getRoomName());
        assertEquals(10, savedRoom.getCapacity());
        assertEquals("Projector, Whiteboard", savedRoom.getFeatures());
    }

    @Test
    void shouldGetRoomById() {
        Room room = new Room();
        room.setRoomName("Conference Room B");
        room.setCapacity(15);
        room.setFeatures("Whiteboard, Video Conferencing");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Optional<Room> foundRoom = roomService.getRoomById(1L);
        assertNotNull(foundRoom);
        assertEquals("Conference Room B", foundRoom.get().getRoomName());
        assertEquals(15, foundRoom.get().getCapacity());
    }
}
