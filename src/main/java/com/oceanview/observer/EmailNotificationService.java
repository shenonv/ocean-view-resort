package com.oceanview.observer;

import com.oceanview.model.Reservation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Observer that sends a booking confirmation email to the guest
 * whenever a new reservation is created.
 *
 * Credentials are loaded from: src/main/resources/email.properties
 * Copy email.properties.example → email.properties and fill in your values.
 */
public class EmailNotificationService implements ReservationObserver {

    private final String smtpHost;
    private final int smtpPort;
    private final String senderEmail;
    private final String senderPassword;

    public EmailNotificationService() {
        Properties config = loadConfig();
        this.smtpHost = config.getProperty("email.smtp.host", "smtp.gmail.com");
        this.smtpPort = Integer.parseInt(config.getProperty("email.smtp.port", "587"));
        this.senderEmail = config.getProperty("email.sender");
        this.senderPassword = config.getProperty("email.password");
    }

    // ── Load email.properties from classpath ─────────────────────────────────
    private Properties loadConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("email.properties")) {

            if (input == null) {
                System.err.println("[EmailNotificationService] email.properties not found in classpath. " +
                        "Copy email.properties.example → email.properties and fill in your credentials.");
                return props;
            }
            props.load(input);
        } catch (IOException e) {
            System.err.println("[EmailNotificationService] Failed to load email.properties: " + e.getMessage());
        }
        return props;
    }

    // ── Observer callback ─────────────────────────────────────────────────────
    @Override
    public void onReservationCreated(Reservation reservation) {
        String guestEmail = reservation.getEmail();

        if (guestEmail == null || guestEmail.isBlank()) {
            System.out.println("[EmailNotificationService] No email for guest '"
                    + reservation.getGuestName() + "' — skipping.");
            return;
        }

        if (senderEmail == null || senderPassword == null) {
            System.err.println("[EmailNotificationService] Email credentials not configured. Check email.properties.");
            return;
        }

        try {
            sendEmail(reservation);
            System.out.println("[EmailNotificationService] Confirmation email sent to: " + guestEmail);
        } catch (MessagingException e) {
            // Log but do NOT crash the reservation flow
            System.err.println("[EmailNotificationService] Failed to send email: " + e.getMessage());
        }
    }

    // ── Send via SMTP ─────────────────────────────────────────────────────────
    private void sendEmail(Reservation reservation) throws MessagingException {

        // 1. SMTP properties
        Properties smtpProps = new Properties();
        smtpProps.put("mail.smtp.auth", "true");
        smtpProps.put("mail.smtp.starttls.enable", "true");
        smtpProps.put("mail.smtp.host", smtpHost);
        smtpProps.put("mail.smtp.port", smtpPort);

        // 2. Session with authenticator
        Session session = Session.getInstance(smtpProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // 3. Build the message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(reservation.getEmail()));
        message.setSubject("🌊 Your Reservation is Confirmed – OceanView Resort");
        message.setContent(buildEmailBody(reservation), "text/html; charset=utf-8");

        // 4. Send
        Transport.send(message);
    }

    // ── HTML email body ───────────────────────────────────────────────────────
    private String buildEmailBody(Reservation reservation) {
        return "<!DOCTYPE html>" +
                "<html><body style='font-family:Arial,sans-serif; color:#333;'>" +
                "<div style='max-width:600px; margin:auto; border:1px solid #dce3e8; border-radius:8px; overflow:hidden;'>"
                +

                "<div style='background:#0d5c8f; padding:24px; text-align:center;'>" +
                "  <h1 style='color:#fff; margin:0;'>🌊 OceanView Resort</h1>" +
                "  <p style='color:#cce5f7; margin-top:6px;'>Booking Confirmation</p>" +
                "</div>" +

                "<div style='padding:28px;'>" +
                "  <p>Dear <strong>" + reservation.getGuestName() + "</strong>,</p>" +
                "  <p>Thank you for choosing OceanView Resort! Your reservation has been confirmed.</p>" +
                "  <table style='width:100%; border-collapse:collapse; margin:20px 0;'>" +
                "    <tr style='background:#f0f7ff;'>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'><strong>Reservation ID</strong></td>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'>" + reservation.getReservationId() + "</td>"
                +
                "    </tr>" +
                "    <tr>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'><strong>Room Type</strong></td>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'>" + reservation.getRoomType() + "</td>" +
                "    </tr>" +
                "    <tr style='background:#f0f7ff;'>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'><strong>Check-In</strong></td>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'>" + reservation.getCheckInDate() + "</td>" +
                "    </tr>" +
                "    <tr>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'><strong>Check-Out</strong></td>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'>" + reservation.getCheckOutDate() + "</td>" +
                "    </tr>" +
                "    <tr style='background:#f0f7ff;'>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'><strong>Contact</strong></td>" +
                "      <td style='padding:10px; border:1px solid #dce3e8;'>" + reservation.getContactNumber() + "</td>"
                +
                "    </tr>" +
                "  </table>" +
                "  <p>We look forward to welcoming you. If you have any questions, reply to this email.</p>" +
                "  <p style='margin-top:30px;'>Warm regards,<br><strong>OceanView Resort Team</strong></p>" +
                "</div>" +

                "<div style='background:#f5f5f5; padding:14px; text-align:center; font-size:12px; color:#888;'>" +
                "  © 2025 OceanView Resort. All rights reserved." +
                "</div>" +

                "</div></body></html>";
    }
}
