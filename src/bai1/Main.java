package bai1;
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "ninh2006";

    public static boolean login(String doctorCode, String password) {
        String sql = "SELECT * FROM doctors WHERE doctor_code = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, doctorCode);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Lỗi kết nối DB!");
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("ĐĂNG NHẬP BÁC SĨ");

        System.out.print("Nhập mã bác sĩ: ");
        String doctorCode = sc.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String password = sc.nextLine();

        boolean result = login(doctorCode, password);

        if (result) {
            System.out.println(" Đăng nhập thành công!");
        } else {
            System.out.println(" Sai tài khoản hoặc mật khẩu!");
        }

        sc.close();
    }
}
