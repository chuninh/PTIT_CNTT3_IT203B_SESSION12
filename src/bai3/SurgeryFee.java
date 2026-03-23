package bai3;

import java.sql.*;
import java.math.BigDecimal;

public class SurgeryFee {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String user = "root";
        String password = "ninh2006";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            CallableStatement cs = conn.prepareCall("{CALL GET_SURGERY_FEE(?, ?)}");
            cs.setInt(1, 1);
            cs.registerOutParameter(2, Types.DECIMAL);

            cs.execute();
            BigDecimal totalCost = cs.getBigDecimal(2);
            System.out.println("Chi phí phẫu thuật: " + totalCost);
            cs.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
