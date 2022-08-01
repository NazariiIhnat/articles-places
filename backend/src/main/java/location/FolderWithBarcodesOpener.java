package location;

import components.MainFrame;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandlerImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class FolderWithBarcodesOpener implements ActionListener {

    private ImageHandlerImpl imageHandler;

    @Autowired
    public FolderWithBarcodesOpener(MainFrame frame, ImageHandlerImpl imageHandler) {
        this.imageHandler = imageHandler;
        frame.getMenu().getOpenFolderWithBarcodesMenuItem().addActionListener(this);
    }


    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        String absolutePath = "explorer.exe " + imageHandler.getImagesFolderPath();
        Runtime.getRuntime().exec(absolutePath);
    }
}
