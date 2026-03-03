package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ReservationDAO {
    public boolean save(Reservation reservation) {

        String sql = "INSERT INTO reservations " +
                "(guest_name, address, contact_number, room_type, check_in_date, check_out_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("DB Connection: " + con);

            ps.setString(1, reservation.getGuestName());
            ps.setString(2, reservation.getAddress());
            ps.setString(3, reservation.getContactNumber());
            ps.setString(4, reservation.getRoomType());
            ps.setDate(5, java.sql.Date.valueOf(reservation.getCheckInDate()));
            ps.setDate(6, java.sql.Date.valueOf(reservation.getCheckOutDate()));

            int rows = ps.executeUpdate();
            System.out.println("Rows inserted: " + rows);

            return rows > 0;

        } catch (Exception e) {
            System.out.println("===== DATABASE ERROR =====");
            e.printStackTrace();
            return false;
        }
    }
}
