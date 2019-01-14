package sneakylabs;

import com.google.common.base.Throwables;
import com.sun.mail.imap.IMAPFolder;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by François Martin on 2019-01-14.
 */
public class EmailService {

  private static final Logger LOGGER =
      LogManager.getLogger(EmailHandler.class.getName());

  private IMAPFolder getFolder(Store store, String folderName) throws MessagingException {
    IMAPFolder folder = (IMAPFolder) store.getFolder(folderName);
    if (!folder.isOpen()) {
      folder.open(Folder.READ_WRITE);
    }
    return folder;
  }

  private void connect(EmailHandler.Connection imap, EmailHandler.Connection smtp) throws MessagingException {
    // connect IMAP
    imap.prop = new Properties();
    imap.session = Session.getInstance(imap.prop);
    if (LOGGER.getLevel().equals(Level.TRACE)) {
      imap.session.setDebug(true);
    }
    imap.store = imap.session.getStore("imaps");
    imap.store.connect(imap.host, imap.username, imap.password);
    LOGGER.info("IMAP connected");

    // connect SMTP
    smtp.prop = new Properties();
    smtp.prop.put("mail.smtp.starttls.enable", "true");
    smtp.prop.put("mail.smtp.host", smtp.host);
    smtp.session = Session.getInstance(smtp.prop);
    if (LOGGER.getLevel().equals(Level.TRACE)) {
      smtp.session.setDebug(true);
    }
  }

  private String[] folderList(Store store) throws MessagingException {
    System.out.println(store);

    Folder[] f = store.getDefaultFolder().list();
    return Arrays.stream(f).map(folder -> folder.getName()).toArray(String[]::new);
  }

  /**
   * Folders must be open already!
   * Messages needs to belong to the "from" folder. If it is null, all messages will be used.
   * Returns true if successful, false if unsuccessful.
   */
  private boolean moveMessages(Folder from, Folder to, Message[] messages, EmailHandler.Settings settings) throws MessagingException {
    // get a list of javamail messages as an array of messages
    if (messages == null) {
      LOGGER.trace("messages is null, copying all messages");
      // get all messages
      messages = from.getMessages();
    }
    LOGGER.info("Moving " + messages.length + " messages...");

    if (copyMessages(from, to, messages)) {
      boolean success = true;
      if (settings.recipient.length() > 0) {
        for (Message message : messages) {
          if (success && !forwardMessage(message, settings)) {
            success = false;
          }
        }
      }
      if (success) {
        return deleteMessages(from, messages);
      }
    }
    return false;
  }

  /**
   * Folders must be open already!
   * Messages needs to belong to the "from" folder. If it is null, all messages will be used.
   * Returns true if successful, false if unsuccessful.
   */
  private boolean copyMessages(Folder from, Folder to, Message[] messages) throws MessagingException {
    // get counts before the operations
    int fromCount = from.getMessageCount();
    int toCount = to.getMessageCount();
    LOGGER.trace("BEFORE - from: " + fromCount + " to: " + toCount);

    // copy the messages to the other folder
    from.copyMessages(messages, to);
    LOGGER.trace("Copied messages");

    // check if the messages have been successfully copied over to the target folder
    if (checkAmount(to, toCount + messages.length)) {
      // copy was successful, delete from source folder
      LOGGER.trace("AFTER - from: " + from.getMessageCount() + " to: " + to.getMessageCount());
      LOGGER.trace("Messages were copied successfully");
      return true;
    } else {
      // copy was not successful, abort
      LOGGER.warn("Target folder used to have " + toCount + " messages, now has " + to.getMessageCount() + " messages but should have " + (toCount + messages.length) + " messages.");
    }
    return false;
  }

  /**
   * @param folder
   * @param messages
   * @return true if successful
   * @throws MessagingException
   */
  private boolean deleteMessages(Folder folder, Message[] messages) throws MessagingException {
    LOGGER.trace("Deleting messages...");

    int folderCount = folder.getMessageCount();

    // flag all messages for deletion
    for (Message message : messages) {
      message.setFlag(Flags.Flag.DELETED, true);
    }

    // delete messages
    folder.expunge();

    // check if deletion was successful
    if (checkAmount(folder, folderCount - messages.length)) {
      LOGGER.trace("Deletion successful");
      return true;
    }
    // deletion was not successful
    LOGGER.error("Source folder used to have " + folderCount + " messages, now has " + folder.getMessageCount() + " messages but should have " + (folderCount - messages.length) + " messages.");
    return false;
  }

  private boolean forwardMessage(Message message, EmailHandler.Settings settings) {
    LOGGER.trace("Forwarding Messages...");
    try {
      // Get all the information from the message
      String from = settings.smtp.username;
      String subject = message.getSubject();
      Date sent = message.getSentDate();
      LOGGER.trace("From: " + message.getFrom() + ", Subject: " + subject + ", Date: " + sent);
      LOGGER_EMAILS.info(message.getFrom().toString()); // log all email addresses to a file

      // compose the message to forward
      Message message2 = new MimeMessage(settings.smtp.session);
      message2.setSubject(settings.SUBJECT_PREFIX + subject);
      message2.setFrom(new InternetAddress(from));
      message2.addRecipient(Message.RecipientType.TO,
          new InternetAddress(settings.recipient));
      message2.setSentDate(sent);
      message2.setReplyTo(message.getReplyTo());

      // Create your new message part
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setDataHandler(message.getDataHandler());

      // Create a multi-part to combine the parts
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      // Associate multi-part with message
      message2.setContent(multipart);

      // Send message
      Transport.send(message2, settings.smtp.username, settings.smtp.password);
    } catch (MessagingException e) {
      LOGGER.error("MessagingException: " + e.toString());
      LOGGER_EXCEPTION.debug(Throwables.getStackTraceAsString(e));
      return false;
    }
    LOGGER.trace("Message successfully forwarded");
    return true;
  }

}
