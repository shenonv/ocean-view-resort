package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class ReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("reservationId"));
        String guestName = request.getParameter("guestName");
        String address = request.getParameter("address");
        String contact = request.getParameter("contactNumber");
        String roomType = request.getParameter("roomType");
        LocalDate checkIn = LocalDate.parse(request.getParameter("checkInDate"));
        LocalDate checkOut = LocalDate.parse(request.getParameter("checkOutDate"));

        Reservation reservation = new Reservation(
                id, guestName, address, contact,
                roomType, checkIn, checkOut
        );

        ReservationService service = new ReservationService();
        service.createReservation(reservation);

        response.getWriter().println("Reservation Added Successfully!");
    }
}