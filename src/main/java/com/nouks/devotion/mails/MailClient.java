package com.nouks.devotion.mails;

import com.nouks.devotion.constants.ApiConstants;
import com.nouks.devotion.domain.dtos.data.EmailAddress;
import com.nouks.devotion.domain.dtos.data.MailCtxDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class MailClient {
    private JavaMailSender infoMailSender;
    private JavaMailSender noReplyMailSender;
    private TemplateEngine htmlTemplateEngine;
    @Value("${spring.application.name}")
    private String appName;
    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/email-simple";
    private static final String EMAIL_RESET_PASSWORD_TEMPLATE = "html/password-reset";
    private static final String EMAIL_REFERRAL_TEMPLATE = "html/referral-mail";
    private static final String EMAIL_NOTIFICATION_TEMPLATE = "html/notification-mail";
    private static final String EMAIL_ACTIVATION_TEMPLATE = "html/activation-mail";
    private static final String EMAIL_GET_STARTED_TEMPLATE = "html/get-started-mail";
    private static final String EMAIL_VOUCHER_TEMPLATE = "html/voucher-mail";
    private static final String EMAIL_VOUCHER_REQUEST_TEMPLATE = "html/voucher-request-mail";
    private static final String EMAIL_PAYMENT_DETAIL_TEMPLATE = "html/payment-details-mail";
    private Logger logger = LoggerFactory.getLogger(MailClient.class);

  public MailClient() {
  }

  @Autowired
  @Qualifier("info-mail")
  public void setInfoMailSender(JavaMailSender infoMailSender) {
    this.infoMailSender = infoMailSender;
  }

  @Autowired
  @Qualifier("no-reply-mail")
  public void setNoReplyMailSender(JavaMailSender noReplyMailSender) {
    this.noReplyMailSender = noReplyMailSender;
  }

  @Autowired
  @Qualifier("emailTemplateEngine")
  public void setHtmlTemplateEngine(TemplateEngine htmlTemplateEngine) {
    this.htmlTemplateEngine = htmlTemplateEngine;
  }

    /*
     * Send HTML mail (simple)
     */
    public void sendActivationMail(
            final EmailAddress recipient, final String fromAddress, final String subject, final Locale locale, List<MailCtxDto> mailCtxList) {
        sendMails(Collections.singletonList(recipient), fromAddress, subject, locale, mailCtxList, EMAIL_ACTIVATION_TEMPLATE);
    }
  public void sendWelcomeMail(
    final EmailAddress recipient, final String fromAddress, final String subject, final Locale locale, List<MailCtxDto> mailCtxList) {
    sendMails(Collections.singletonList(recipient), fromAddress, subject, locale, mailCtxList, EMAIL_GET_STARTED_TEMPLATE);
  }

  public void sendResetPasswordMail(
    final EmailAddress recipient,
    final String fromAddress,
    final String subject,
    final Locale locale,
    List<MailCtxDto> mailCtxList
  ){
    sendMails(Collections.singletonList(recipient), fromAddress, subject, locale, mailCtxList, EMAIL_RESET_PASSWORD_TEMPLATE);
  }

    public void sendMails(List<EmailAddress> recipients, final String fromAddress,
                          final String subject, final Locale locale, List<MailCtxDto> mailCtxList, String template) {
        for (EmailAddress emailAddress: recipients) {
            try {
                mailCtxList.add(new MailCtxDto("name", emailAddress.getName()));
                sendInfoMail(
                        emailAddress, fromAddress,
                        subject, locale, template, mailCtxList
                );
            } catch (Exception e) {
              e.printStackTrace();
                logger.info("Failed to send mail to: {}<{}>", emailAddress.getName(), emailAddress.getEmail());
            }
        }
    }

    public void sendAdminNotificationMail(
            List<EmailAddress> recipients, final String fromAddress,
            final String subject, final Locale locale, List<MailCtxDto> mailCtxList) {
        sendMails(recipients, fromAddress, subject, locale, mailCtxList, EMAIL_NOTIFICATION_TEMPLATE);
    }

    private JavaMailSender getMailSender(String mailType){
      if (mailType.equals(ApiConstants.ACCOUNT_ACTIVATION_FROM_ADDRESS)) return this.noReplyMailSender;
      else return this.infoMailSender;
    }

    public void sendMail (final EmailAddress recipient, final String url, final String fromAddress, final String subject, final Locale locale, String templateName)
            throws MessagingException {
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipient.getName() == null ? "Customer" : recipient.getName());
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("appName", appName);
        ctx.setVariable("activationUrl", url);

        // Prepare message using a Spring helper
        JavaMailSender javaMailSender = getMailSender(fromAddress);
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(fromAddress);
        message.setTo(recipient.getEmail());

        String htmlContent =  this.htmlTemplateEngine.process(templateName, ctx);
        logger.info(htmlContent);
        // Create the HTML body using Thymeleaf
        message.setText(htmlContent, true /* isHtml */);
        logger.info("-----< Sending Email to: ... {} >-----", recipient.getEmail());
        // Send email
        javaMailSender.send(mimeMessage);
        logger.info("-----< Email Sent >-----");
    }

    public void sendInfoMail(final EmailAddress recipient,
                             final String fromAddress,
                             final String subject, final Locale locale, String templateName, List<MailCtxDto> mailCtxList)
            throws MessagingException {
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        mailCtxList.forEach(
                mailCtxDto -> ctx.setVariable(mailCtxDto.getKey(), mailCtxDto.getValue())
        );
        // Prepare message using a Spring helper
      JavaMailSender javaMailSender = getMailSender(fromAddress);
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(fromAddress);
        message.setTo(recipient.getEmail());

        String htmlContent =  this.htmlTemplateEngine.process(templateName, ctx);
        // Create the HTML body using Thymeleaf
        message.setText(htmlContent, true /* isHtml */);
        logger.info("-----< Sending Email to: ... {} >-----", recipient.getEmail());
        // Send email
        javaMailSender.send(mimeMessage);
        logger.info("-----< Email Sent >-----");
    }

    /*public void sendResetPasswordMail(EmailAddress user, String resetUrl, String accountActivationFromAddress, String accountActivationSubject, Locale locale)
    throws MessagingException {
        sendMail(user, resetUrl, accountActivationFromAddress, accountActivationSubject, locale, EMAIL_RESET_PASSWORD_TEMPLATE);
    }*/

}
