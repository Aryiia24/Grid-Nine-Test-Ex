package com.gridnine.testing;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        FlightFilter flightFilter = new FlightFilter();
        List<Flight> testFlights = FlightBuilder.createFlights();

        List<Flight> filteredFlightByRule1 = flightFilter.filterFlightsBeforeCurrentTime(testFlights);
        List<Flight> filteredFlightByRule2 = flightFilter.filterFlightsOnWrongArrivalDate(filteredFlightByRule1);
        List<Flight> filteredFlightByRule3 = flightFilter.filterFlightsByEarthTime(filteredFlightByRule2);

        System.out.println("Test list flight");
        System.out.println(testFlights);
        System.out.println("----------------");
        System.out.println("Filtered list of flights by departure time to the current time:");
        System.out.println(filteredFlightByRule1);
        System.out.println("----------------");
        System.out.println("Filtered list of flights by wrong arrival date:");
        System.out.println(filteredFlightByRule2);
        System.out.println("----------------");
        System.out.println("Filtered list of flights by the total time spent on the ground for more than two hours:");
        System.out.println(filteredFlightByRule3);

    }

}
