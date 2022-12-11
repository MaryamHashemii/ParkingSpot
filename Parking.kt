package com.example.trainingforinterview.talpa

fun main() {
    val parking = Parking(10, 5)
    parking.parkVehicle(VehicleType.TRUCK)
    parking.unPark(VehicleType.TRUCK)
}
class Parking(var freeCarSpot: Int, var freeTruckSpot: Int) {

    private var carSpotList = mutableListOf<Vehicle>()
    private var truckSpotList = mutableListOf<Vehicle>()
    private var bikeSpotList = mutableListOf<Vehicle>()
    private var freeBikeSpot: Int = 0

    fun parkVehicle(vehicle: VehicleType) {
        if (vehicle == VehicleType.TRUCK) {
            if (freeTruckSpot > 0) {
                parkYourTruck()
            } else {
                println("there is no enough space for your truck")
            }
        } else if (vehicle == VehicleType.CAR) {
            if (freeCarSpot > 0) {
                parkYourCar()
            } else if (freeTruckSpot > 0) {
                parkYourCarInTruckSpot()
            } else {
                println("there is no enough space for your car")
            }
        } else if (vehicle == VehicleType.BIKE) {
            if (freeBikeSpot in 1..2) {
                parkYourBike()
            } else if ((freeTruckSpot * 3) > 0) {
                parkYourBike()
            } else {
                println("there is no enough space for your bike")
            }
        }
//        println("$freeCarSpot $freeTruckSpot $freeBikeSpot")

    }

    private fun parkYourCarInTruckSpot() {
        carSpotList.add(Vehicle(VehicleType.CAR, Destination.TRUCK))
        freeTruckSpot--
        freeCarSpot++
        println("we successfully park your car")
    }

    private fun parkYourBike() {
        bikeSpotList.add(Vehicle(VehicleType.BIKE, Destination.TRUCK))
        println("we successfully park your bike")
        if (freeBikeSpot % 3 == 0) {
            freeTruckSpot--
            freeBikeSpot = 2
        } else {
            freeBikeSpot--
        }
    }

    private fun parkYourCar() {
        carSpotList.add(Vehicle(VehicleType.CAR, Destination.CAR))
        println("we successfully park your car")
        freeCarSpot--
    }

    private fun parkYourTruck() {
        truckSpotList.add(Vehicle(VehicleType.TRUCK, Destination.TRUCK))
        println("we successfully park your truck")
        freeTruckSpot--
        if (freeCarSpot > 10)
            freeCarSpot -= 2
    }

    fun unPark(vehicle: VehicleType) {
        if (vehicle == VehicleType.TRUCK) {
            if (truckSpotList.size > 0) {
                println("successfully unpark")
                freeTruckSpot++
                truckSpotList.removeLast()
            } else {
                println("there is no truck here")
            }
        } else if (vehicle == VehicleType.CAR) {
            if (carSpotList.size > 0) {
                println("successfully unpark")
                if (carSpotList.last().destinationType == Destination.TRUCK) {
                    freeTruckSpot++
                    freeCarSpot--
                    carSpotList.removeLast()
                } else {
                    freeCarSpot++
                    carSpotList.removeLast()
                }
            } else {
                println("there is no car here")
            }
        } else if (vehicle == VehicleType.BIKE) {
            if (bikeSpotList.size > 0) {
                println("successfully unpark")
                freeBikeSpot++
                bikeSpotList.removeLast()
                increaseTruckSpotByBike(freeBikeSpot)
            } else {
                println("there is no bike here")
            }
        }
//        println("$freeCarSpot $freeTruckSpot $freeBikeSpot")
    }

    private fun increaseTruckSpotByBike(numberOfFreeBikeSpot: Int) {
        if (numberOfFreeBikeSpot != 0 && numberOfFreeBikeSpot % 3 == 0) {
            freeTruckSpot += numberOfFreeBikeSpot / 3
            freeBikeSpot -= 3
        }
    }
}

sealed class VehicleType {
    object CAR : VehicleType()
    object BIKE : VehicleType()
    object TRUCK : VehicleType()

}

data class Vehicle(
    val vehicleType: VehicleType,
    val destinationType: Destination
)

sealed class Destination {
    object CAR : Destination()
    object TRUCK : Destination()
}