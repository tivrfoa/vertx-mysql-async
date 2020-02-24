package vertx.test;

import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

/**
 * https://vertx.io/docs/vertx-mysql-client/java/
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        testMySQL();
    }

    public static void testMySQL() {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
          .setPort(3306)
          .setHost("localhost")
          .setDatabase("chat1")
          .setUser("root")
          .setPassword("abc123")
          .setCharset("utf8");

        // Pool options
        PoolOptions poolOptions = new PoolOptions()
          .setMaxSize(5);

        // Create the client pool
        MySQLPool client = MySQLPool.pool(connectOptions, poolOptions);

        // A simple query
        client.query("SELECT * FROM user WHERE id > 0", ar -> {
          if (ar.succeeded()) {
            RowSet<Row> result = ar.result();
            System.out.println("Got " + result.size() + " rows ");
          } else {
            System.out.println("Failure: " + ar.cause().getMessage());
          }

          // Now close the pool
          client.close();
        });

        System.out.println("THE END");
    }
}
