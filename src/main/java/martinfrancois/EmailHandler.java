package martinfrancois;

import com.google.common.base.Throwables;
import com.sun.mail.imap.IMAPFolder;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by François Martin on 10.09.2017.
 */
public class EmailHandler {

  public static final int AMOUNT_ARGUMENTS = 3;
  private static final Logger LOGGER =
      LogManager.getLogger(EmailHandler.class.getName());
  private static final Logger LOGGER_EMAILS = LogManager.getLogger("Emails");
  private static final Logger LOGGER_EXCEPTION = LogManager.getLogger("Exception");
  private static final SecurePreferences pref = new SecurePreferences();
/*
  public static void main(String[] args) {
    int numOfAccounts = 0;
    String recipient = "";

    // if no input is given - clear preferences
    if (args.length == 0) {
      pref.resetPrefs();
      LOGGER.info("Preferences cleared");
    }

    if (args.length % AMOUNT_ARGUMENTS == 0) {
      // without recipient
      numOfAccounts = args.length / AMOUNT_ARGUMENTS;
    } else if ((args.length - 1) % AMOUNT_ARGUMENTS == 0) {
      // with recipient
      numOfAccounts = (args.length - 1) / AMOUNT_ARGUMENTS;
      recipient = args[args.length - 1];
    } else {
      // incorrect
      LOGGER.error("Incorrect number of arguments found (" + args.length + ").");
    }

    if (numOfAccounts > 0) {
      LOGGER.trace(numOfAccounts + " accounts");
      for (int i = 0; i < numOfAccounts; i++) {
        LOGGER.trace("Current account: " + (i + 1));

        String hostImap = args[0 + (AMOUNT_ARGUMENTS * i)];
        String hostSmtp = args[1 + (AMOUNT_ARGUMENTS * i)];
        String username = args[2 + (AMOUNT_ARGUMENTS * i)];
        String password = pref.loadPref(username);
        if (password.length() == 0) {
          LOGGER.trace("Password not found");
          System.out.println("Please enter password for user: " + username);
          Scanner in = new Scanner(System.in);
          password = in.nextLine().trim();
          pref.savePref(username, password);
          LOGGER.trace("Password saved");
        } else{
          LOGGER.trace("Password found");
        }

        Connection imap = new Connection(hostImap, username, password);
        Connection smtp = new Connection(hostSmtp, username, password);

        Account account = new Account(imap, smtp, recipient);

        moveSpam(account);
      }
    }

  }

  private static void moveSpam(Account account) {
    LOGGER.info("Trying to connect to host: " + account.imap.host + " with user: " + account.imap.username);
    try {
      connect(account.imap, account.smtp);

      //open the folders
      IMAPFolder junk = getFolder(account.imap.store, "Junk");
      IMAPFolder inbox = getFolder(account.imap.store, "Inbox");
      LOGGER.trace("Folders opened");

      boolean successful = moveMessages(junk, inbox, null, account);
      LOGGER.info("Success: " + successful);

      account.imap.store.close();
    } catch (NoSuchProviderException nspe) {
      LOGGER.error("NoSuchProviderException: " + nspe.toString());
      LOGGER_EXCEPTION.debug(Throwables.getStackTraceAsString(nspe));
    } catch (MessagingException me) {
      LOGGER.error("MessagingException: " + me.toString());
      LOGGER_EXCEPTION.debug(Throwables.getStackTraceAsString(me));
    }
  }

  private static boolean checkAmount(Folder folder, int expected) throws MessagingException {
    int threshold = 20;
    int attempt = 0;
    int actual = -1;
    do {
      attempt++;
      actual = folder.getMessageCount();
      LOGGER.trace("Attempt: " + attempt + ", Expected messages: " + expected + " Actual messages: " + actual);
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        // interrupted
      }
    } while (attempt < threshold && (actual != expected));
    return attempt != threshold;
  }
*/

}
