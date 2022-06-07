package components.location;
import javax.swing.*;

public class ShowMessage {
    public static void error(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
