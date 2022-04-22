package utils.resources;

import java.awt.*;

@FunctionalInterface
public interface LambdaLoader {
    Image getImage(ImageIdentifier imageIdentifier);
}
