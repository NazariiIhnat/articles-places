package location;

import components.MainFrame;
import components.location.ShowMessage;
import components.location.TreeItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class Searcher implements KeyListener {

    private JTextField textField;
    private TreeItemList tree;

    @Autowired
    public Searcher(MainFrame frame) {
        this.textField = frame.getSearchLocationTextFiled();
        this.tree = frame.getTreeItemList();
        frame.getSearchLocationTextFiled().addKeyListener(this);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = textField.getText();
            long id;
            try {
                id = Long.parseLong(text);
                tree.getNavigableTreeModel().selectNodeByID(id);
            } catch (NumberFormatException ex) {
                ShowMessage.error("Kod \"" + text + "\" jest nieprawidłowy");
            } catch (NullPointerException ex) {
                ShowMessage.error("Nie znaleziono lokacji z ID = " + text);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
