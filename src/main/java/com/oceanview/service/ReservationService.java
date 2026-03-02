package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.factory.RoomFactory;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;

public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();

    public boolean createReservation(Reservation reservation) {
        if (reservation.getCheckOutDate()
                .isBefore(reservation.getCheckInDate())) {
            throw new IllegalArgumentException("Invalid reservation dates");
        }
        Room room = RoomFactory.createRoom(reservation.getRoomType());

        System.out.println("Room type: " + room.getType());
        System.out.println("Room per night: " + room.getPricePerNight());

        return reservationDAO.save(reservation);
    }
}
