package bai2;

import java.sql.*;

public class UpdateVitalSigns {

    public static void updateVitals(int patientId, double temperature, int heartRate) {
        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String user = "root";
        String password = "ninh2006";

        String sql = "UPDATE patients SET temperature = ?, heart_rate = ? WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, temperature);
            ps.setInt(2, heartRate);
            ps.setInt(3, patientId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân!");
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        updateVitals(1, 37.5, 80);
    }
}
