package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilter {

    private static final LocalDateTime CURRENT_DATE = LocalDateTime.now();
    private static final int COUNT_HOURS_ON_EARTH = 2;

    public List<Flight> filterFlightsBeforeCurrentTime(List<Flight> inputListFlights) {
        return inputListFlights.stream()
                .filter(f -> f.getSegments().stream().noneMatch(s -> s.getDepartureDate().isBefore(CURRENT_DATE)))
                .collect(Collectors.toList());
    }

    public List<Flight> filterFlightsOnWrongArrivalDate(List<Flight> inputListFlights) {
        return inputListFlights.stream()
                .filter(f -> f.getSegments().stream().allMatch(s -> s.getDepartureDate().isBefore(s.getArrivalDate())))
                .collect(Collectors.toList());
    }

    public List<Flight> filterFlightsByEarthTime(List<Flight> inputListFlights) {
        List<Flight> filteredList = new ArrayList<>();
        for (Flight flight : inputListFlights) {
            int hoursOnEarth = 0;
            Object[] segments = flight.getSegments().toArray();
            for (int i = 0; i < segments.length - 1; i++) {
                LocalDateTime departureDateNextSegment = ((Segment) segments[i + 1]).getDepartureDate();
                LocalDateTime arrivalDateCurrentSegment = ((Segment) segments[i]).getArrivalDate();
                Duration duration = Duration.between(arrivalDateCurrentSegment, departureDateNextSegment);
                hoursOnEarth += duration.toHours();
            }
            if (hoursOnEarth < COUNT_HOURS_ON_EARTH) {
                filteredList.add(flight);
            }
        }
        return filteredList;
    }
}
