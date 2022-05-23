package utils;
import entities.Container;
import net.sourceforge.barbecue.*;
import net.sourceforge.barbecue.output.OutputException;
import java.awt.image.BufferedImage;

public class BarcodeHandler {

    public static BufferedImage createBarcodeImageFromContainer(Container container) {
        Barcode barcode;
        BufferedImage image = null;
        try {
            barcode = BarcodeFactory.createCode128(String.valueOf(container.getId()));
            barcode.setLabel(container.getUserReadableInfo().toUpperCase());
            barcode.setBarWidth(calculateBarcodeWidth(container.getUserReadableInfo()));
            image = BarcodeImageHandler.getImage(barcode);
        } catch (BarcodeException | OutputException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static int calculateBarcodeWidth(String text) {
        int textLength = text.toCharArray().length;
        if(textLength/5 < 2)
            return 2;
        else
            return textLength/5;
    }
}
