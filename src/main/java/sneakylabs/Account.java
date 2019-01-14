package sneakylabs;

public class Account {
  Connection imap;
  Connection smtp;

  public Account(Connection imap, Connection smtp) {
    this.imap = imap;
    this.smtp = smtp;
  }
}
