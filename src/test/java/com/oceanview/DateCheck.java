package com.oceanview;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;
import junit.framework.TestCase;

import java.time.LocalDate;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class DateCheck extends TestCase {
    public void testInvalidDatesThrowException() {
        ReservationService service = new ReservationService();
        Reservation r = new Reservation();
        r.setCheckInDate(LocalDate.of(2026, 4, 10));
        r.setCheckOutDate(LocalDate.of(2026, 4, 5));
        r.setRoomType("Standard");
        try {
            service.createReservation(r);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid reservation dates", e.getMessage());
        }
    }
}
