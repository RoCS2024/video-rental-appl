package org.rocs.vra.domain.email.constant;

/**
 * Centralizes email configuration constants for the Video Rental System.
 */
public class EmailConstant {


    /** SMTP protocol with implicit TLS/SSL (smtps). */
    public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";

    /** Email account username (sender). */
    public static final String USERNAME = "bsit.501.2025@gmail.com";

    /** App‑specific password for the Gmail account. */
    public static final String PASSWORD = "rpgc qhiv cdzn imhi";

    /** From address used for outgoing emails. */
    public static final String FROM_EMAIL = "bsit.501.2025@gmail.com";

    /** Default subject line for password‑related emails. */
    public static final String EMAIL_SUBJECT = "Welcome to Video Rental System: Your New Password";

    /** Gmail's SMTP server hostname. */
    public static final String GMAIL_SMTP_SERVER = "smtp.gmail.com";

    /** Property key for the SMTP host. */
    public static final String SMTP_HOST = "mail.smtp.host";

    /** Property key for enabling SMTP authentication. */
    public static final String SMTP_AUTH = "mail.smtp.auth";

    /** Property key for the SMTP port number. */
    public static final String SMTP_PORT = "mail.smtp.port";

    /** Default SMTP port (465 for SSL/TLS). */
    public static final int DEFAULT_PORT = 465;

    /** Property key for enabling STARTTLS. */
    public static final String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    /** Property key for requiring STARTTLS. */
    public static final String SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
}
