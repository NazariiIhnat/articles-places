package utils;

import entities.Location;

import java.awt.image.BufferedImage;

public interface ImageHandler {
    void save(Location location);
    void update(Location location);
    BufferedImage get(Location location);
    void delete(Location location);
}
