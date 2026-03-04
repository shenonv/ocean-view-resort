package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.util.DBConnection;

import java.sql.*;

public class ReservationDAO {

    public int save(Reservation reservation) {

        String sql = "INSERT INTO reservations " +
                "(guest_name, email, address, contact_number, room_type, check_in_date, check_out_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reservation.getGuestName());
            ps.setString(2, reservation.getEmail());
            ps.setString(3, reservation.getAddress());
            ps.setString(4, reservation.getContactNumber());
            ps.setString(5, reservation.getRoomType());
            ps.setDate(6, Date.valueOf(reservation.getCheckInDate()));
            ps.setDate(7, Date.valueOf(reservation.getCheckOutDate()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("Generated ID: " + generatedId);
                    return generatedId;
                }
            }

        } catch (Exception e) {
            System.out.println("===== DATABASE ERROR =====");
            e.printStackTrace();
        }

        return -1; // if insert failed
    }

    public Reservation findById(int id) {

        String sql = "SELECT * FROM reservations WHERE reservation_id = ?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setGuestName(rs.getString("guest_name"));
                reservation.setEmail(rs.getString("email"));
                reservation.setAddress(rs.getString("address"));
                reservation.setContactNumber(rs.getString("contact_number"));
                reservation.setRoomType(rs.getString("room_type"));
                reservation.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
                reservation.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());

                return reservation;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}