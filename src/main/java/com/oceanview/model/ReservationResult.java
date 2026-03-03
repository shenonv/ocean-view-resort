package com.oceanview.model;

public class ReservationResult {

    private int reservationId;
    private double totalBill;

    public ReservationResult(int reservationId, double totalBill) {
        this.reservationId = reservationId;
        this.totalBill = totalBill;
    }

    public int getReservationId() {
        return reservationId;
    }

    public double getTotalBill() {
        return totalBill;
    }
}