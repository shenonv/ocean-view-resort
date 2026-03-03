package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/printBill")
public class PrintBillServlet extends HttpServlet {

    private ReservationService service = new ReservationService();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        // 🔥 Retrieve full reservation from DB
        Reservation reservation = service.getReservation(reservationId);

        request.setAttribute("reservation", reservation);
        request.setAttribute("reservationId", reservationId);

        request.getRequestDispatcher("printBill.jsp")
                .forward(request, response);
    }
}