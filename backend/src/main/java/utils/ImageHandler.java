package utils;

import entities.Container;

import java.awt.image.BufferedImage;

public interface ImageHandler {
    void save(Container container);
    void update(Container container);
    BufferedImage get(Container container);
    void delete(Container container);
}
