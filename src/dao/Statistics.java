package dao;

import admin.AdminDashboard;
import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import supplier.SupplierDashboard;
import user.UserDashboard;

/**
 *
 * @author User
 */
public class Statistics {

    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    private int total(String tablename) {
        int total = 0;
        try {
            Connection con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select count(*) from " + tablename + "");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double totalSales() {
        double total = 0.0;
        try {
            Connection con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) from purchase");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double totalPurchase(int id) {
        double total = 0.0;
        try {
            Connection con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) from purchase where uid=" + id + "");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double todaytotalSales() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        String today = df.format(date);
        double total = 0.0;
        try {
            Connection con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) from purchase where p_date='" + today + "'");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public void admin() {
        AdminDashboard.jLabel25.setText(String.valueOf(total("category")));
        AdminDashboard.jLabel24.setText(String.valueOf(total("product")));
        AdminDashboard.jLabel27.setText(String.valueOf(total("user")));
        AdminDashboard.jLabel31.setText(String.valueOf(total("supplier")));
        AdminDashboard.jLabel33.setText(String.valueOf(totalSales()));
        AdminDashboard.jLabel32.setText(String.valueOf(todaytotalSales()));
    }

    public void user(int id) {
        UserDashboard.jLabel25.setText(String.valueOf(total("category")));
        UserDashboard.jLabel24.setText(String.valueOf(total("product")));
        UserDashboard.jLabel27.setText(String.valueOf(totalPurchase(id)));
    }

    private int totalDeliveries(String name) {
        int total = 0;
        try {
            Connection con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select count(*) from purchase where supplier='" + name + "' and status='Received'");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public void supplier(String name) {
        SupplierDashboard.jLabel25.setText(String.valueOf(totalDeliveries(name)));
    }

}
