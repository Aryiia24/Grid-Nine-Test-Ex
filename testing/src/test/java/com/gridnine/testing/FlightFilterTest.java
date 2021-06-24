package com.gridnine.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FlightFilterTest {

    private FlightFilter flightFilter = new FlightFilter();
    private List<Flight> testList = FlightBuilder.createFlights();

    @Test
    @DisplayName("Method should remove flights that have arrival date before current date.")
    void testFilterFlightsBeforeCurrentTime() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Flight> resultList = flightFilter.filterFlightsBeforeCurrentTime(testList);
        for (Flight flight : resultList) {
            LocalDateTime departureDate = flight.getSegments().get(0).getDepartureDate();
            assertTrue(departureDate.isAfter(currentDate));
        }
    }

    @Test
    @DisplayName("Method should remove flights that have segments with an arrival date earlier than the departure date.")
    void testFilterFlightsOnWrongArrivalDate() {
        List<Flight> resultList = flightFilter.filterFlightsOnWrongArrivalDate(testList);

        assertTrue(resultList.stream().allMatch(
                f -> f.getSegments().stream().allMatch(s -> s.getDepartureDate().isBefore(s.getArrivalDate()))));
    }

    @Test
    @DisplayName("Method should remove flights that have total earth time more than two hours.")
    void testFilterFlightsByEarthTime() {
        List<Flight> resultList = flightFilter.filterFlightsByEarthTime(testList);
        int maxHoursOnEarth = 2;
        int actualHoursOnEarth = 0;
        for (Flight flight : resultList) {
            Object[] segments = flight.getSegments().toArray();
            for (int i = 0; i < segments.length - 1; i++) {
                LocalDateTime departureDateNextSegment = ((Segment) segments[i + 1]).getDepartureDate();
                LocalDateTime arrivalDateCurrentSegment = ((Segment) segments[i]).getArrivalDate();
                Duration duration = Duration.between(arrivalDateCurrentSegment, departureDateNextSegment);
                actualHoursOnEarth += duration.toHours();
            }
        }
        assertTrue(actualHoursOnEarth < maxHoursOnEarth);
    }
}
