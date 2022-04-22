package utils.resources;

import java.awt.*;

public class ImageManager {
    private static ImageManager imageManager = new ImageManager();

    private final String RESOURCES_PATH = "src/main/resources/";
    private final ImagePathFinder imagePathFinder;
    private final ImageCache imageCache;

    private ImageManager() {
        imagePathFinder = new ImagePathFinder();
        imageCache = new ImageCache();
    }

    public static ImageManager getInstance() {
        return imageManager;
    }

    public static Image getImage(ImageIdentifier imageIdentifier) {
        return getInstance().getImageByInstance(imageIdentifier);
    }

    public Image getImageByInstance(ImageIdentifier imageIdentifier) {
        return imageCache.getImage(imageIdentifier,
                identifier -> Toolkit.getDefaultToolkit().getImage(getFullPath(identifier))
                );
    }

    private String getFullPath(ImageIdentifier imageIdentifier) {
        return RESOURCES_PATH + imagePathFinder.getPath(imageIdentifier);
    }
}
