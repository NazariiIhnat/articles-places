package location;

import components.MainFrame;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Component
public class FolderWithBarcodesOpener implements ActionListener {

    private MainFrame frame;

    @Autowired
    public FolderWithBarcodesOpener(MainFrame frame) {
        this.frame = frame;
        frame.getMenu().getOpenFolderWithBarcodesMenuItem().addActionListener(this);
    }


    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File("backend/src/main/java/barcode/images/");
        String absolutePath = "explorer.exe " + file.getAbsolutePath();
        Runtime.getRuntime().exec(absolutePath);
    }
}
