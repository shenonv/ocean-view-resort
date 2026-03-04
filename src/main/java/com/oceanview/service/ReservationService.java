package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.factory.RoomFactory;
import com.oceanview.factory.StandardRoomFactory;
import com.oceanview.factory.DeluxeRoomFactory;
import com.oceanview.factory.SuiteRoomFactory;
import com.oceanview.factory.BillingStrategyFactory;
import com.oceanview.model.Reservation;
import com.oceanview.model.ReservationResult;
import com.oceanview.model.Room;
import com.oceanview.strategy.BillingStrategy;
import com.oceanview.observer.ReservationObserver;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();

    // Observer list
    private List<ReservationObserver> observers = new ArrayList<>();

    // Register observer
    public void addObserver(ReservationObserver observer) {
        observers.add(observer);
    }

    // Notify observers
    private void notifyObservers(Reservation reservation) {
        for (ReservationObserver observer : observers) {
            observer.onReservationCreated(reservation);
        }
    }

    public ReservationResult createReservation(Reservation reservation) {

        if (reservation.getCheckOutDate()
                .isBefore(reservation.getCheckInDate())) {
            throw new IllegalArgumentException("Invalid reservation dates");
        }
        String roomType = reservation.getRoomType();
        RoomFactory roomFactory;
        if (roomType.equalsIgnoreCase("Standard")) {
            roomFactory = new StandardRoomFactory();
        } else if (roomType.equalsIgnoreCase("Deluxe")) {
            roomFactory = new DeluxeRoomFactory();
        } else if (roomType.equalsIgnoreCase("Suite")) {
            roomFactory = new SuiteRoomFactory();
        } else {
            throw new IllegalArgumentException("Invalid room type: " + roomType);
        }
        Room room = roomFactory.createRoom();

        long nights = ChronoUnit.DAYS.between(
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());

        BillingStrategy strategy = BillingStrategyFactory.getStrategy(room.getType());

        double totalBill = strategy.calculateBill(
                (int) nights,
                room.getPricePerNight());
        int generatedId = reservationDAO.save(reservation);

        if (generatedId == -1) {
            throw new RuntimeException("Failed to save reservation");
        }

        reservation.setReservationId(generatedId);
        notifyObservers(reservation);

        return new ReservationResult(generatedId, totalBill);
    }

    public Reservation getReservation(int id) {
        return reservationDAO.findById(id);
    }
}