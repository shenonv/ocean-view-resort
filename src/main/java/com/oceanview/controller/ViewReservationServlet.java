package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/viewReservation")
public class ViewReservationServlet extends HttpServlet {

    private ReservationService service = new ReservationService();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("reservationId"));

        Reservation reservation = service.getReservation(id);

        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("viewReservation.jsp")
                .forward(request, response);
    }
}