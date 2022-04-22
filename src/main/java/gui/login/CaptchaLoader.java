package gui.login;

import logic.menus.login.Captcha;
import logic.menus.login.CaptchaManager;

import javax.swing.*;
import java.awt.*;

public class CaptchaLoader {
    private static CaptchaLoader captchaLoader = new CaptchaLoader();
    private static CaptchaManager captchaManager = CaptchaManager.getInstance();

    private CaptchaLoader() {}

    public static CaptchaLoader getInstance() {
        return captchaLoader;
    }

    public static ImageIcon getCaptchaImageIcon() {
        return getInstance().getImageIcon();
    }

    public static boolean captchaNumberIsValid(String captchaNumberString) {
        int captchaNumber = Integer.parseInt(captchaNumberString);
        Captcha currentCaptcha = captchaManager.getCurrentCaptcha();
        return captchaNumber == currentCaptcha.getCaptchaNumber();
    }

    private ImageIcon getImageIcon() {
        Image captchaImage = captchaManager.getCaptchaImage();
        return new ImageIcon(captchaImage);
    }
}