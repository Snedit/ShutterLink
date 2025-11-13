package com.shutterlink.auth_service.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public void sendOtpMail(String to, String otp) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f8f9fa; padding: 20px;">
                    <div style="max-width: 500px; margin: auto; background-color: #ffffff;
                                border-radius: 10px; box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                                padding: 25px; text-align: center;">
                        <h2 style="color: #2d89ef;">🔐 ShutterLink Verification</h2>
                        <p style="font-size: 16px; color: #333;">Your One-Time Password (OTP) is:</p>
                        <div style="font-size: 28px; font-weight: bold; color: #2d89ef; margin: 15px 0;">
                            %s
                        </div>
                        <p style="color: #555;">This code will expire in <strong>10 minutes</strong>.</p>
                        <p style="font-size: 14px; color: #999;">If you didn’t request this, please ignore this email.</p>
                        <hr style="margin: 25px 0;">
                        <p style="font-size: 12px; color: #aaa;">© 2025 ShutterLink. All rights reserved.</p>
                    </div>
                </body>
                </html>
            """.formatted(otp);

            helper.setTo(to);
            helper.setSubject("Your ShutterLink OTP");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            logger.info("✅ OTP email sent successfully to {}", to);

        } catch (MessagingException e) {
            logger.error("❌ Failed to send OTP email to {}: {}", to, e.getMessage());
        }
    }
}
