package org.mufasadev.mshando.core.security.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(final String to,final EmailTemplate emailTemplate,final String username, final String confirmationUrl, final String subject, final String activationCode)
    throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        }
        else templateName = emailTemplate.name();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
        Map<String, Object> properties  = new HashMap<>();
        properties.put("activationCode", activationCode);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("username", username);

        Context context = new Context();
        context.setVariables(properties);

        messageHelper.setFrom("mufasadev@zohomail.com");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);

        String template = templateEngine.process(templateName, context);
        messageHelper.setText(template, true);
        mailSender.send(message);
    }
}