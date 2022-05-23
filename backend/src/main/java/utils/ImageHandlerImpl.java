package utils;

import entities.Container;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class ImageHandlerImpl implements ImageHandler {

    private final String imagesRootFolder = "backend/src/main/java/barcode/images/";

    @Override
    public void save(Container container) {
        BufferedImage image = BarcodeHandler.createBarcodeImageFromContainer(container);
        File file = new File(imagesRootFolder + getFilename(container));
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Container container = new Container();
        container.setId(15);
        container.setUserReadableInfo("123456789111314wef eger gwerg rth wrth egqrg qegqwef qwef");
        ImageHandler handler = new ImageHandlerImpl();
        handler.save(container);
    }

    private String getFilename(Container container) {
        return container.getUserReadableInfo() + "_ID" + container.getId() + ".png";
    }

    @Override
    public void update(Container container) {
        String filepathToDelete = getFilenameByContainerID(container);
        File file = new File(imagesRootFolder + filepathToDelete);
        file.delete();
        save(container);
    }

    private String getFilenameByContainerID(Container container) {
        File file = new File(imagesRootFolder);
        File[] files = file
                .listFiles(pathname -> pathname.getName().split("_ID")[1].equals(container.getId() + ".png"));
        System.out.println("qdqwdqwd" + files.length);
        return files[0].getName();
    }

    @Override
    public BufferedImage get(Container container) {
        try {
            return ImageIO.read(new URL(imagesRootFolder + getFilename(container)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Container container) {
        File file = new File(imagesRootFolder + getFilename(container));
        file.delete();
    }
}
