package utils.resources;

import java.awt.*;
import java.util.EnumMap;

public class ImageCache {
    private final EnumMap<ImageIdentifier, Image> cacheMap;

    public ImageCache() {
        cacheMap = new EnumMap<>(ImageIdentifier.class);
    }

    public void addToCache(ImageIdentifier imageIdentifier, Image image) {
        cacheMap.put(imageIdentifier, image);
    }

    public Image getImage(ImageIdentifier imageIdentifier, LambdaLoader loader) {
        return cacheMap.computeIfAbsent(imageIdentifier, loader::getImage);
    }
}
