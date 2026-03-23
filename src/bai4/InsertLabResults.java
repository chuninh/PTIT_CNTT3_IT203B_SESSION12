package bai4;

import java.sql.*;

public class InsertLabResults {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String user = "root";
        String password = "ninh2006";

        String sql = "INSERT INTO lab_results (patient_id, result_value) VALUES (?, ?)";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 1000; i++) {

                ps.setInt(1, i);          
                ps.setDouble(2, 5.5 + i); 

                ps.executeUpdate();
            }

            ps.close();
            conn.close();

            System.out.println("Insert xong!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
