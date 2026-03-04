package com.oceanview.observer;

import com.oceanview.model.Reservation;

public interface ReservationObserver {
    void onReservationCreated(Reservation reservation);
}
