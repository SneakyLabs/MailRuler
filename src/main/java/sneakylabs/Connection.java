package sneakylabs;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Created by François Martin on 2019-01-14.
 */
public class Connection {
  String host;
  String username;
  String password;
  Properties prop;
  Session session;
  Store store;

  public Connection(String host, String username, String password) {
    this.host = host;
    this.username = username;
    this.password = password;
  }
}
