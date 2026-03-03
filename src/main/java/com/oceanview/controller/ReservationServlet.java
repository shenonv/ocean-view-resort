package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/addReservation")
public class ReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String guestName = request.getParameter("guestName");
        String address = request.getParameter("address");
        String contact = request.getParameter("contactNumber");
        String roomType = request.getParameter("roomType");
        LocalDate checkIn = LocalDate.parse(request.getParameter("checkInDate"));
        LocalDate checkOut = LocalDate.parse(request.getParameter("checkOutDate"));

        Reservation reservation = new Reservation();
        reservation.setGuestName(guestName);
        reservation.setAddress(address);
        reservation.setContactNumber(contact);
        reservation.setRoomType(roomType);
        reservation.setCheckInDate(checkIn);
        reservation.setCheckOutDate(checkOut);

        ReservationService service = new ReservationService();

        double bill = service.createReservation(reservation);

        request.setAttribute("totalBill", bill);
        request.getRequestDispatcher("reservationSuccess.jsp")
                .forward(request, response);
    }
}