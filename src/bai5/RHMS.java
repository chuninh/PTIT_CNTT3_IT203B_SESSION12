package bai5;
import java.sql.*;
import java.util.Scanner;
import java.math.BigDecimal;

public class RHMS {
    static String url = "jdbc:mysql://localhost:3306/hospital_db";
    static String user = "root";
    static String password = "ninh2006";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n===== RHMS MENU =====");
                System.out.println("1. Danh sách bệnh nhân");
                System.out.println("2. Tiếp nhận bệnh nhân");
                System.out.println("3. Cập nhật bệnh án");
                System.out.println("4. Xuất viện & tính phí");
                System.out.println("5. Thoát");
                System.out.print("Chọn: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        listPatients(conn);
                        break;
                    case 2:
                        addPatient(conn, sc);
                        break;
                    case 3:
                        updateDisease(conn, sc);
                        break;
                    case 4:
                        discharge(conn, sc);
                        break;
                    case 5:
                        conn.close();
                        System.out.println("Thoát!");
                        return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void listPatients(Connection conn) throws Exception {
        String sql = "SELECT * FROM patients";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getInt("age") + " | " +
                            rs.getString("department")
            );
        }
    }

    static void addPatient(Connection conn, Scanner sc) throws Exception {
        String sql = "INSERT INTO patients(name, age, department, disease, admission_date) VALUES (?, ?, ?, ?, NOW())";

        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.print("Tên: ");
        ps.setString(1, sc.nextLine());

        System.out.print("Tuổi: ");
        ps.setInt(2, sc.nextInt());
        sc.nextLine();

        System.out.print("Khoa: ");
        ps.setString(3, sc.nextLine());

        System.out.print("Bệnh: ");
        ps.setString(4, sc.nextLine());

        ps.executeUpdate();
        System.out.println(" Thêm thành công");
    }

    static void updateDisease(Connection conn, Scanner sc) throws Exception {
        String sql = "UPDATE patients SET disease = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.print("ID bệnh nhân: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Bệnh mới: ");
        String disease = sc.nextLine();

        ps.setString(1, disease);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();

        if (rows > 0) System.out.println("Cập nhật thành công");
        else System.out.println(" Không tìm thấy bệnh nhân");
    }

    static void discharge(Connection conn, Scanner sc) throws Exception {
        System.out.print("Nhập ID bệnh nhân: ");
        int id = sc.nextInt();

        CallableStatement cs = conn.prepareCall("{CALL CALCULATE_DISCHARGE_FEE(?, ?)}");

        cs.setInt(1, id);
        cs.registerOutParameter(2, Types.DECIMAL);

        cs.execute();

        BigDecimal fee = cs.getBigDecimal(2);

        System.out.println(" Viện phí: " + fee);
    }
}
