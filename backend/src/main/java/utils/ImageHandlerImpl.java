package utils;

import entities.Location;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class ImageHandlerImpl implements ImageHandler {

    @Getter(AccessLevel.PUBLIC)
    private String imagesFolderPath;

    public ImageHandlerImpl() {
        String imagesFolderName = "barcode images";
        File imagesFolder = new File("./" + imagesFolderName);
        if(!imagesFolder.exists())
            imagesFolder.mkdir();
        try {
            imagesFolderPath = imagesFolder.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ImageHandler handler = new ImageHandlerImpl();
        Location location = new Location();
        location.setUserReadableInfo("test");
        handler.save(location);
    }

    @Override
    public void save(Location location) {
        BufferedImage image = BarcodeHandler.createBarcodeImageFromLocation(location);
        File file = new File(imagesFolderPath + "/" +getFilename(location));
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilename(Location location) {
        return location.getUserReadableInfo() + "_ID" + location.getId() + ".png";
    }

    @Override
    public void update(Location location) {
        String filepathToDelete = getFilenameByLocationID(location);
        File file = new File(imagesFolderPath + "/" + filepathToDelete);
        file.delete();
        save(location);
    }

    private String getFilenameByLocationID(Location location) {
        File file = new File(imagesFolderPath + "/");
        File[] files = file
                .listFiles(pathname -> pathname.getName().split("_ID")[1].equals(location.getId() + ".png"));
        return files[0].getName();
    }

    @Override
    public BufferedImage get(Location location) {
        try {
            return ImageIO.read(new URL(imagesFolderPath + "/" + getFilename(location)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Location location) {
        File file = new File(imagesFolderPath + "/" + getFilename(location));
        file.delete();
    }
}
