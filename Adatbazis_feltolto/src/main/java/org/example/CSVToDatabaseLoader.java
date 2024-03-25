import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CSVToDatabaseLoader {

    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DATABASE_URL = "jdbc:sqlite:test.db";
    static final String USER = "";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            String csvFile = "kolcsonzok.csv";
            String line;
            String csvSplitBy = ",";

            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                String sql = "INSERT INTO Kolcsonzok(id, nev, szulIdo) VALUES(?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(data[0]));
                preparedStatement.setString(2, data[1]);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                java.util.Date parsedDate = dateFormat.parse(data[2]);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                preparedStatement.setTimestamp(3, timestamp);

                preparedStatement.executeUpdate();
            }

            csvFile = "kolcsonzesek.csv";

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                String sql = "INSERT INTO Kolcsonzesek(id, kolcsonzokId, iro, mufaj, cim) VALUES(?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, Integer.parseInt(data[0]));
                preparedStatement.setInt(2, Integer.parseInt(data[1]));
                preparedStatement.setString(3, data[2]);
                preparedStatement.setString(4, data[3]);
                preparedStatement.setString(5, data[4]);

                preparedStatement.executeUpdate();
            }

            br.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
