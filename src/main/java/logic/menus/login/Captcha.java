package logic.menus.login;

import utils.resources.ImageIdentifier;
import utils.resources.ImageManager;

import java.awt.*;

public class Captcha {
    private int captchaNumber;
    private Image captchaImage;

    public Captcha(int captchaNumber) {
        this.captchaNumber = captchaNumber;
        setCaptchaImage();
    }

    public void setCaptchaImage() {
        ImageIdentifier imageIdentifier = getImageIdentifier();
        captchaImage = ImageManager.getImage(imageIdentifier);
    }

    private ImageIdentifier getImageIdentifier() {
        switch (captchaNumber) {
            case 5710:
                return ImageIdentifier.CAPTCHA_5710;
            case 7447:
                return ImageIdentifier.CAPTCHA_7447;
            case 8843:
                return ImageIdentifier.CAPTCHA_8843;
            case 8947:
                return ImageIdentifier.CAPTCHA_8947;
            case 9125:
                return ImageIdentifier.CAPTCHA_9125;
            default:
                return null;
        }
    }

    public int getCaptchaNumber() {
        return captchaNumber;
    }

    public Image getCaptchaImage() {
        return captchaImage;
    }
}
