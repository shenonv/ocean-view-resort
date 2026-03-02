package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ReservationDAO {
    public boolean save(Reservation reservation) {
        String sql = "INSERT INTO reservations VALUES (?,?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, reservation.getReservationId());
            ps.setString(2, reservation.getGuestName());
            ps.setString(3, reservation.getAddress());
            ps.setString(4, reservation.getContactNumber());
            ps.setString(5, reservation.getRoomType());
            ps.setDate(6, Date.valueOf(reservation.getCheckInDate()));
            ps.setDate(7, Date.valueOf(reservation.getCheckOutDate()));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
