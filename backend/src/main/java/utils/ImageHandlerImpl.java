package utils;

import entities.Location;
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
    public void save(Location location) {
        BufferedImage image = BarcodeHandler.createBarcodeImageFromLocation(location);
        File file = new File(imagesRootFolder + getFilename(location));
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Location location = new Location();
        location.setId(15);
        location.setUserReadableInfo("123456789111314wef eger gwerg rth wrth egqrg qegqwef qwef");
        ImageHandler handler = new ImageHandlerImpl();
        handler.save(location);
    }

    private String getFilename(Location location) {
        return location.getUserReadableInfo() + "_ID" + location.getId() + ".png";
    }

    @Override
    public void update(Location location) {
        String filepathToDelete = getFilenameByLocationID(location);
        File file = new File(imagesRootFolder + filepathToDelete);
        file.delete();
        save(location);
    }

    private String getFilenameByLocationID(Location location) {
        File file = new File(imagesRootFolder);
        File[] files = file
                .listFiles(pathname -> pathname.getName().split("_ID")[1].equals(location.getId() + ".png"));
        System.out.println("qdqwdqwd" + files.length);
        return files[0].getName();
    }

    @Override
    public BufferedImage get(Location location) {
        try {
            return ImageIO.read(new URL(imagesRootFolder + getFilename(location)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Location location) {
        File file = new File(imagesRootFolder + getFilename(location));
        file.delete();
    }
}
