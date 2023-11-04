package com.sipl.vehicle.schedule.mail;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sipl.vehicle.repository.VehicleRepository;

@Component
public class ScheduleMail {
    private final JavaMailSender javaMailSender;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public ScheduleMail(JavaMailSender javaMailSender, VehicleRepository vehicleRepository) {
        this.javaMailSender = javaMailSender;
        this.vehicleRepository = vehicleRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void scheduleMessage() {
        try {
            // Fetch the total record count from the database
            long recordCount = vehicleRepository.count();

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            
            // Set the recipient email address
            helper.setTo("vjpawar995@gmail.com");

            // Set the email subject
            helper.setSubject("Total Vehicle Records Count");

            // Set the email text with the record count
            String message = "Total vehicle records in the database: " + recordCount;
            helper.setText(message, true); // Use 'true' for HTML content

            // Send the email
            javaMailSender.send(mimeMessage);
            System.out.println("Message sent Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message not sent successfully.");
        }
    }
}
