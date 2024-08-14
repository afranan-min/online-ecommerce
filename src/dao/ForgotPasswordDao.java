
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
import user.ForgotPassword;

/**
 *
 * @author User
 */
public class ForgotPasswordDao {
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
     public boolean isEmailExist(String email){              
        try {
            Connection con= MyConnection.getConnection();
            ps=con.prepareStatement("select * from user where uemail=?");
            ps.setString(1, email);
            rs=ps.executeQuery();
            if(rs.next()){
                ForgotPassword.jTextField1.setText(rs.getString(6));
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"Email address does not exist");
                return false;
            }       
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;     
    }
     public boolean getAns(String email,String newAns){              
        try {
            Connection con= MyConnection.getConnection();
            ps=con.prepareStatement("select * from user where uemail=?");
            ps.setString(1, email);
            rs=ps.executeQuery();
            if(rs.next()){
                String oldAns=rs.getString(7);
                if(newAns.equals(oldAns)){
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null,"Sequrity answer does not match");
                    return false;
                }
                
            }   
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;     
    }
      public boolean setPassword(String email,String pass){
          String sql="update user set upassword=? where uemail=?";
        try {
            
            Connection con= MyConnection.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, pass);
             ps.setString(2, email);
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Password updated successfully");
                return true;
            }  
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;     
    }
}
