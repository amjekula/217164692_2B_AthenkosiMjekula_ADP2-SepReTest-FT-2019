package CarPartSer;
/**
 *
 * @author 217164692
 */
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CreateCarPartsSer {

    Connection con;
    PreparedStatement prepState;
    ObjectInputStream in;
    ObjectOutputStream out;
    String serFile = "carParts.ser";


    public static void main(String args[]) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("carParts.ser"));
            output.writeObject(new CarParts(101, "Oil filter", 045.89, 055.89, 28, false));
            output.writeObject(new CarParts(201, "Spark Plugs", 025.00, 035.00, 128, true));
            output.writeObject(new CarParts(301, "Fan Belt", 135.99, 138.99, 010, false));
            output.writeObject(new CarParts(401, "12A16W Bulbs", 005.95, 007.95, 004, false));
            output.writeObject(new CarParts(501, "Brake Fluid", 029.09, 049.09, 90, true));
            output.writeObject(new CarParts(601, "Engine Oil 5L", 076.00, 089.00, 122, false));
            output.writeObject(new CarParts(701, "Fuel Filter", 018.98, 028.98, 19, true));

            output.close();
        }//end try
        catch (IOException fnfe) {
            System.out.println(fnfe);
            System.exit(1);
        }

        CreateCarPartsSer c = new CreateCarPartsSer();
        c.connect();
        c.init();
        c.openFiles();
        c.readCarPartsFile();
        c.executeCarPartsQuery();
        c.closeFiles();
        c.closeConnections();

    }//end main

    public Connection connect() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\A. Mjeks\\Documents\\NetBeansProjects\\3rd Year\\217164692_2B_AthenkosiMjekula_ADP2-SepReTest-FT-2019\\publisher.mdb");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return con;
    }

    public void init() {
        String createTable = "create table CARPARTS(catNumber INTEGER, description VARCHAR(30), purchPrice DECIMAL, sellPrice DECIMAL,"
                + " quantity INTEGER, inStock BOOLEAN)";
        try {
            prepState = con.prepareStatement(createTable);
            prepState.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public void closeConnections() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public void openFiles() {
        try {
            in = new ObjectInputStream(new FileInputStream(serFile));

        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }
    }

    public void closeFiles() {
        try {
            in.close();

        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }
    }

    public void readCarPartsFile() {
        String query = "insert into CARPARTS(catNumber, description, purchPrice, sellPrice,"
                + " quantity, inStock) values (?,?,?,?,?,?)";

        while (true) {
            try {
                CarParts parts = (CarParts) in.readObject();
                prepState = con.prepareStatement(query);

                prepState.setInt(1, parts.getCatNumber());
                prepState.setString(2, parts.getDescription());
                prepState.setDouble(3, parts.getPurchPrice());
                prepState.setDouble(4, parts.getSellingPrice());
                prepState.setInt(5, parts.getQuantitySold());
                prepState.setBoolean(6, parts.getInStock());

                prepState.executeUpdate();

            } catch (EOFException ex) {
                JOptionPane.showMessageDialog(null, "Inserted Into Database Successfully");
                return;

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("IOException | ClassNotFoundException: " + ex.getMessage());
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        }
    }

    public void executeCarPartsQuery(){
        String query = "Select * from CARPARTS";
        String txtFile = "Retest2019Out.txt";

        try {
            prepState = con.prepareStatement(query);
            ResultSet resSet = prepState.executeQuery();

            BufferedWriter write = new BufferedWriter(new FileWriter(txtFile));

            write.write("Cat\tDescription\tPurch Pr\tSell Pr\t\tQuantity\tStock");
            write.newLine();
            write.write("----\t-------------\t------\t\t---------\t--------\t------");
            write.newLine();

            while(resSet.next()){
                write.write(resSet.getInt(1)+"\t"+resSet.getString(2)+"\t"+resSet.getDouble(3)+"\t\t"
                        + resSet.getDouble(4)+"\t\t"+resSet.getInt(5)+"\t\t"+resSet.getBoolean(6));
                write.newLine();
            }
            JOptionPane.showMessageDialog(null, "Text File Created Successfully");
            write.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }
    }

}
