package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserDao {

    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public int getMaxRow() {
        int row = 0;
        try {
            Connection con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select max(uid) from user");
            while (rs.next()) {
                row = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public boolean isEmailExist(String email) {
        try {
            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement("select * from user where uemail=?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isPhoneExist(String phone) {
        try {
            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement("select * from user where uphone=?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(int id, String username, String email, String pass, String phone, String seq, String ans, String address1, String address2) {
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, phone);
            ps.setString(6, seq);
            ps.setString(7, ans);
            ps.setString(8, address1);
            ps.setString(9, address2);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User added successfully.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String[] getUserValue(int id) {
        String[] value = new String[9];
        try {
            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement("select * from user where uid=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);
                value[6] = rs.getString(7);
                value[7] = rs.getString(8);
                value[8] = rs.getString(9);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    public boolean update(String email, String pass) {
        String sql = "update user set upassword=? where uemail=?";
        try {

            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, email);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Password updated successfully");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updatee(int id, String username, String email, String pass, String phone, String seq, String ans, String address1, String address2) {
        String sql = "UPDATE user SET uname=?, uemail=?, upassword=?, uphone=?, usecqus=?, uans=?, uaddress1=?, uaddress2=? WHERE uid=?";
        try {
            Connection con = MyConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            // Set the parameters
            ps.setString(1, username);       // Set username
            ps.setString(2, email);          // Set email
            ps.setString(3, pass);           // Set password
            ps.setString(4, phone);          // Set phone
            ps.setString(5, seq);            // Set security question
            ps.setString(6, ans);            // Set answer
            ps.setString(7, address1);       // Set address line 1
            ps.setString(8, address2);       // Set address line 2
            ps.setInt(9, id);                // Set user ID

            // Execute the update
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User details updated successfully");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM user WHERE uid=?";
        try {
            Connection con = MyConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id); // Set the user ID parameter

            // Execute the delete statement
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User deleted successfully");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getUserId(String email) {
        int id = 0;
        try {
            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement("select uid from user where uemail=?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    //get users data
    public void getUsersValue(JTable table, String search) {
        String sql = "select * from user where concat(uid,uname,uemail) like ? order by uid desc";
        try {
            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[9];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
