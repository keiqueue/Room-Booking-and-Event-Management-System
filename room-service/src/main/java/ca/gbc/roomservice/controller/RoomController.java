/**
 * COMP 3095 - Group Assignment
 * @Auhtors: Jam Furaque, Andrew Stewart, Kei Ishikawa, Carl Trinidad
 * */
package ca.gbc.roomservice.controller;

import ca.gbc.roomservice.model.Room;
import ca.gbc.roomservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            if (roomService.roomExistsByName(room.getRoomName())) {
                return new ResponseEntity<>("Room with name '" + room.getRoomName() + "' already exists", HttpStatus.CONFLICT);
            }
            Room savedRoom = roomService.saveRoom(room);
            return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create room: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        try {
            List<Room> rooms = roomService.getAllRooms();
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch rooms: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> availableRooms = roomService.getAvailableRooms();
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        if (room.isPresent()) {
            return new ResponseEntity<>(room.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Room with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        try {
            Room updatedRoom = roomService.updateRoom(id, roomDetails);
            System.out.println("Successfully updated room with ID " + id + ":");
            System.out.println("New Details: " + updatedRoom);
            System.out.println("Changes made: Room Name - " + roomDetails.getRoomName() + ", Capacity - " + roomDetails.getCapacity() + ", Features - " + roomDetails.getFeatures() + ", Available - " + roomDetails.isAvailable());
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update room: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        if (room.isPresent()) {
            roomService.deleteRoom(id);
            System.out.println("Room with ID " + id + " deleted successfully.");
            return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Room with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
