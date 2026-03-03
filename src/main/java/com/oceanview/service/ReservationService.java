package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.factory.RoomFactory;
import com.oceanview.model.Reservation;
import com.oceanview.model.ReservationResult;
import com.oceanview.model.Room;
import com.oceanview.strategy.*;

import java.time.temporal.ChronoUnit;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();

    public ReservationResult createReservation(Reservation reservation) {

        if (reservation.getCheckOutDate()
                .isBefore(reservation.getCheckInDate())) {
            throw new IllegalArgumentException("Invalid reservation dates");
        }

        Room room = RoomFactory.createRoom(reservation.getRoomType());

        long nights = ChronoUnit.DAYS.between(
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );

        BillingStrategy strategy;

        switch (room.getType().toLowerCase()) {
            case "standard":
                strategy = new StandardBillingStrategy();
                break;
            case "deluxe":
                strategy = new DeluxeBillingStrategy();
                break;
            case "suite":
                strategy = new SuiteBillingStrategy();
                break;
            default:
                throw new IllegalArgumentException("Invalid room type");
        }

        double totalBill = strategy.calculateBill(
                (int) nights,
                room.getPricePerNight()
        );

        int generatedId = reservationDAO.save(reservation);

        if (generatedId == -1) {
            throw new RuntimeException("Failed to save reservation");
        }

        return new ReservationResult(generatedId, totalBill);
    }

    public Reservation getReservation(int id) {
        return reservationDAO.findById(id);
    }
}