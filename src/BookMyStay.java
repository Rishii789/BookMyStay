import java.util.*;

abstract class Room {
    String type;
    int beds;
    int size;
    double price;

    Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    String getType() {
        return type;
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single", 1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double", 2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite", 3, 750, 5000.0);
    }
}

class RoomInventory {
    HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    int getAvailability(String roomType) {
        return inventory.get(roomType);
    }

    void decrement(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }
}

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingRequestQueue {
    Queue<Reservation> queue;

    BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    void addRequest(Reservation r) {
        queue.add(r);
    }

    Reservation getNextRequest() {
        return queue.poll();
    }

    boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class BookingService {
    RoomInventory inventory;
    HashMap<String, Set<String>> allocatedRooms;
    Set<String> usedRoomIds;
    int idCounter = 1;

    BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        usedRoomIds = new HashSet<>();
    }

    void processQueue(BookingRequestQueue queue) {
        System.out.println("Reservation Confirmation & Allocation");
        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            int available = inventory.getAvailability(r.roomType);

            if (available > 0) {
                String roomId = r.roomType + "-" + idCounter++;
                if (!usedRoomIds.contains(roomId)) {
                    usedRoomIds.add(roomId);

                    allocatedRooms.putIfAbsent(r.roomType, new HashSet<>());
                    allocatedRooms.get(r.roomType).add(roomId);

                    inventory.decrement(r.roomType);

                    System.out.println("Reservation confirmed for Guest: " + r.guestName +
                            ", Room Type: " + r.roomType +
                            ", Assigned Room ID: " + roomId);
                }
            } else {
                System.out.println("No rooms available for Guest: " + r.guestName +
                        ", Room Type: " + r.roomType);
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Double"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        BookingService bookingService = new BookingService(inventory);
        bookingService.processQueue(queue);
    }
}