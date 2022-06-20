import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class javaCrud {
    private JPanel main;
    private JTextField txtpname;
    private JTextField txtprice;
    private JTextField txtqty;
    private JButton saveButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("javaCrud");
        frame.setContentPane(new javaCrud().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtpid;
    private JButton searchButton;

    public javaCrud() {

        Connect();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pname,price,qty;

                pname = txtpname.getText();
                price = txtprice.getText();
                qty = txtqty.getText();

                try {
                    pst = con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
                    pst.setString(1,pname);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product has been added");

                    txtpname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtpname.requestFocus();

                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pid = txtpid.getText();

                    pst = con.prepareStatement("select pname,price,qty from products where pid = ?");
                    pst.setString(1,pid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()==true){
                        String pname = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        txtpname.setText(pname);
                        txtprice.setText(price);
                        txtqty.setText(qty);
                    }
                    else {
                        txtpname.setText("");
                        txtprice.setText("");
                        txtqty.setText("");
                        JOptionPane.showMessageDialog(null,"That Product does not exist");
                    }
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,pname,price,qty;
                pname = txtpname.getText();
                price = txtprice.getText();
                qty = txtqty.getText();
                pid = txtpid.getText();

                try{
                    pst = con.prepareStatement("update products set pname =?, price=?, qty=? where pid=?");
                    pst.setString(1,pname);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"The Product was updated successfully");

                    txtpname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtpid.setText("");
                }
                catch (SQLException e2){
                    e2.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid;

                pid = txtpid.getText();

                try {
                    pst = con.prepareStatement("delete from products where pid = ?");
                    pst.setString(1,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"The product was deleted Successfully!");

                    txtpname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtpid.setText("");

                }
                catch (SQLException e3){
                    e3.printStackTrace();
                }
            }
        });
    }
    Connection con;
    PreparedStatement pst;

    public void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts","root","");
            System.out.println("Success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
