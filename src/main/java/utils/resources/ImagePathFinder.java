package utils.resources;

public class ImagePathFinder {
    private final String captchaPath;
    private final String profilePicturesPath;

    public ImagePathFinder() {
        captchaPath = "assets/captcha/";
        profilePicturesPath = "assets/profilepictures/";
    }

    public String getPath(ImageIdentifier imageIdentifier) {
        switch (imageIdentifier) {
            case CAPTCHA_5710:
                return captchaPath + "5710.jpg";
            case CAPTCHA_7447:
                return captchaPath + "7447.jpg";
            case CAPTCHA_8843:
                return captchaPath + "8843.jpg";
            case CAPTCHA_8947:
                return captchaPath + "8947.jpg";
            case CAPTCHA_9125:
                return captchaPath + "9125.jpg";
            case DEFAULT_PROFILE_PICTURE:
                return profilePicturesPath + "default.png";
            default:
                return null; // makes catching invalid images easier
        }
    }
}
