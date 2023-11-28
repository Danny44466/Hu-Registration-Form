import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Calendar;

public class Studregistrationform extends JDialog {
    private static JFrame parent;
    private JTextField tfuid;
    private JTextField tfdepartment;
    private JTextField tfcollege;
    private JTextField tfage;
    private JTextField tfphone;
    private JTextField tfgender;
    private JTextField tfyear;
    private JTextField tfname;
    private JButton tfbtn;
    private JTextField tfbatch;
    private JPanel registerpanel;
    private JRadioButton radioButton1;

    public Studregistrationform(JFrame parent) {
        super(parent);
        setTitle("Registrate form Regular Student ");
        setContentPane(registerpanel);
        setModal(true);
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tfbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        setVisible(true);
    }

     public User user;

    private void register() {
        String name = tfname.getText();
        String department = tfdepartment.getText();
        int uid = Integer.parseInt(tfuid.getText());
        String college = tfcollege.getText();
        String gender = tfgender.getText();
        int age = Integer.parseInt(tfage.getText());
        int year = Integer.parseInt(tfyear.getText());
        int phone = Integer.parseInt(tfphone.getText());
        String batch = tfbatch.getText();


    user = addUsertoDatabase
            (name, college,
             department, year,
             age, phone, gender, batch);
}
    /*if( User = Null ) {
         dispose();
    }
    else {
        JOptionPane.showMessageDialog(
            this,
              "failed to register student",
              "Try again ",
              JOptionPane.ERROR_MESSAGE
        );
    }*/
private User addUsertoDatabase(String name,String college,String department,int year
, int age,int phone,String gender,String batch ) {
    User user = null;
    final String Username = "root";
    final String Password = "";
    final String DB_URL = "jdbc:mysql://localhost/MyStore/serverTimezone=UTC";
    try {
        Connection conn = DriverManager.getConnection(Username, DB_URL, Password);
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO users(name ,department ,college,gender,phone ,age,year,batch) " +
                "values(?,?,?,?,?,?,?,?,?)";
        int addedrows;
        PreparedStatement PreparedStatement = conn.prepareStatement(sql);
        {


            PreparedStatement.setString(1, name);
            PreparedStatement.setString(2, department);
            PreparedStatement.setString(3, college);
            PreparedStatement.setString(4, gender);
            PreparedStatement.setInt(5, phone);
            PreparedStatement.setInt(6, age);
            PreparedStatement.setInt(7, year);
            PreparedStatement.setString(8, batch);

            addedrows = PreparedStatement.executeUpdate();
        }
        if (addedrows > 0) {
            user = new User();
            user.name = name;
            user.department = department;
            user.college = college;
            user.gender = gender;
            user.phone = phone;
            user.age = age;
            user.year = year;
            user.batch = batch;
        }
        stmt.close();
        conn.close();
    } catch (Exception e) {

        return user;
    }
     public static void main() {
        Studregistrationform s1 = new Studregistrationform(null);
        user = s1.user;
        if (user != null)
            System.out.println("Student" + name + "registrated ");
        else
            System.out.println("registration failed");

    }
    return user;
}}
