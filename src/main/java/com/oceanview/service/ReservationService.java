package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.factory.RoomFactory;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.strategy.BillingStrategy;
import com.oceanview.strategy.DeluxeBillingStrategy;
import com.oceanview.strategy.StandardBillingStrategy;
import com.oceanview.strategy.SuiteBillingStrategy;

import java.time.temporal.ChronoUnit;

public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();

    public double createReservation(Reservation reservation) {
        if (reservation.getCheckOutDate()
                .isBefore(reservation.getCheckInDate())) {
            throw new IllegalArgumentException("Invalid reservation dates");
        }
        //factory pattern to create room based on reservation
        Room room = RoomFactory.createRoom(reservation.getRoomType());

        long nights = ChronoUnit.DAYS.between(
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );

        //strategy pattern to calculate bill based on room type

        BillingStrategy strategy;

        switch (room.getType()) {
            case "Standerd":
                strategy = new StandardBillingStrategy();
                break;
            case "Deluxe":
                strategy = new DeluxeBillingStrategy();
                break;
            case "Suite":
                strategy = new SuiteBillingStrategy();
                break;
            default:
                throw new IllegalArgumentException("Invalid room type");
        }
        double totalBill = strategy.calculateBill(
                (int)nights,
                room.getPricePerNight()
        );
        reservationDAO.save(reservation);
        return totalBill;
    }
}
