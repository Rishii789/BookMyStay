import java.util.HashMap;

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

    void displayRoom(int availability) {
        System.out.println(type + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available Rooms: " + availability);
        System.out.println();
    }

    String getType() {
        return type;
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 750, 5000.0);
    }
}

class RoomInventory {
    HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.get(roomType);
    }
}

class RoomSearchService {
    RoomInventory inventory;

    RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void searchRooms(Room[] rooms) {
        System.out.println("Available Rooms");
        System.out.println();
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());
            if (available > 0) {
                room.displayRoom(available);
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchRooms(rooms);
    }
}