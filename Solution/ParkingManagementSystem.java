import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

class Car {
    String registrationNumber;
    boolean category;
    String checkInTime;
    String checkOutTime;
    int parkingSlotNumber;
    int parkingFloor;
    int charges;

    Car(String registrationNumber, String checkInTime, boolean category) {
        this.registrationNumber = registrationNumber;
        this.checkInTime = checkInTime;
        this.category = category;
    }
}

public class ParkingManagementSystem {
    int numFloors;
    int parkingSlots;
    Set<String> reservedSlots;
    List<Car> cars;
    List<Car> allData;

    ParkingManagementSystem(int numFloors, int parkingSlots, String reservedSlots) {
        this.numFloors = numFloors;
        this.parkingSlots = parkingSlots;
        this.reservedSlots = new HashSet<String>(Arrays.asList(reservedSlots.split(" ")));
        this.cars = new ArrayList<Car>();
        this.allData = new ArrayList<Car>();
    }

    public String checkIn(String registrationNumber, String checkInTime, boolean category) {
        for (int floor = 0; floor < numFloors; floor++) {
            for (int slot = 1; slot <= parkingSlots; slot++) {
                String forSlotCheck = (char) ('A' + floor) + "-" + slot;
                if (reservedSlots.contains(forSlotCheck) && category) {
                    boolean isSlotAvailable = true;
                    for (Car car : cars) {
                        if (slot == car.parkingSlotNumber && floor == car.parkingFloor) {
                            isSlotAvailable = false;
                            break;
                        }
                    }
                    if (isSlotAvailable) {
                        Car car = new Car(registrationNumber, checkInTime, true);
                        car.parkingSlotNumber = slot;
                        car.parkingFloor = floor;
                        cars.add(car);
                        return forSlotCheck;
                    }
                } else if (!reservedSlots.contains(forSlotCheck) && !category) {
                    boolean isSlotAvailable = true;
                    for (Car car : cars) {
                        if (slot == car.parkingSlotNumber && floor == car.parkingFloor) {
                            isSlotAvailable = false;
                            break;
                        }
                    }
                    if (isSlotAvailable) {
                        Car car = new Car(registrationNumber, checkInTime, false);
                        car.parkingSlotNumber = slot;
                        car.parkingFloor = floor;
                        cars.add(car);
                        return forSlotCheck;
                    }
                }
            }
        }
        return "PARKING FULL";
    }

    public int checkOut(String registrationNumber, String checkOutTime) throws ParseException {
        int charges = 0;
        LocalTime localtime1 = LocalTime.parse(checkOutTime);
        for (Car car : cars) {
            if (car.registrationNumber.equals(registrationNumber)) {
                LocalTime localtime2 = LocalTime.parse(car.checkInTime);
                Duration duration = Duration.between(localtime2, localtime1);
                long durationInSeconds = Math.abs(duration.toSeconds());

                if (durationInSeconds <= 7200) {
                    charges = 50;
                } else if (durationInSeconds > 7200 && durationInSeconds <= 14400) {
                    charges = 80;
                } else {
                    charges = 100;
                }
                car.charges = charges;
                car.checkOutTime = checkOutTime;
                allData.add(car);
                cars.remove(car);

                return charges;
            }
        }

        return -1;
    }

    public void GENERATE_REPORT() {

        allData.sort(Comparator.comparing((Car car) -> car.parkingSlotNumber)
                .thenComparing((Car car) -> car.checkInTime));

        System.out.println("PARKING SLOT, CAR NO, CHECK IN TIME, CHECK OUT TIME, CHARGES, CATEGORY");
        for (Car car : allData) {
            String temp = (char) ('A' + car.parkingFloor) + "-" + car.parkingSlotNumber;
            System.out.println(temp
                    + " " + car.registrationNumber
                    + " " + car.checkInTime
                    + " " + car.checkOutTime
                    + " " + car.charges
                    + " " + car.category);
        }

    }

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);

        ParkingManagementSystem parkingCars = new ParkingManagementSystem(1, 5,
                "A-1 A-10 B-2 B-5 C-1 C-8 D-2 D-4 E-5 E-10");

        while (true) {
            String tempStr = sc.nextLine();
            String[] strArray = tempStr.split((" "));
            for (String str : strArray) {
                System.out.println(str);
            }

            if (strArray.length == 4) {
                if (strArray[3].equals("R"))
                    System.out.println(parkingCars.checkIn(strArray[1], strArray[2], true));
                else
                    System.out.println(parkingCars.checkIn(strArray[1], strArray[2], false));
            } else if (strArray.length == 3) {
                System.out.println(parkingCars.checkOut(strArray[1], strArray[2]));
            } else if (strArray.length == 2) {
                // Generate Report
                parkingCars.GENERATE_REPORT();
            } else {
                sc.close();
                break;
            }
        }
    }
}
