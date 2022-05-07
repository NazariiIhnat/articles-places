package barcode;

import components.CreateContainerDialog;
import components.MainFrame;
import dao.ContainerDAO;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

@Component
public class BarcodeGenerator {

    private MainFrame mainFrame;
    private ContainerDAO containerDAO;
    private CreateContainerDialog createContainerDialog;

    @Autowired
    public BarcodeGenerator(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.containerDAO = containerDAO;
        createContainerDialog = mainFrame.getCreateContainerDialog();
        createContainerDialog.getOkBtn().addActionListener(generateBarcode());
    }

    private ActionListener generateBarcode() {
        return e -> {
            String name = createContainerDialog.getTextField().getText();
            Barcode barcode;
            try {
                barcode = BarcodeFactory.createCode128(name);
                saveBarcodeAsJPG(barcode, name);
            } catch (BarcodeException ex) {
                ex.printStackTrace();
            }
        };
    }

    private void saveBarcodeAsJPG(Barcode barcode, String fileName) {
        try{
            File file = new File("backend/src/main/java/barcode/images/"+fileName+".jpg");
            System.out.println(file.getCanonicalPath());
            ImageIO.write(BarcodeImageHandler.getImage(barcode), "jpg", file);
        } catch (IOException | OutputException e) {
            e.printStackTrace();
        }
    }
}
