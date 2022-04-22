package logic.menus.login;

import java.awt.*;
import java.util.LinkedList;

public class CaptchaManager {
    private static CaptchaManager captchaManager = new CaptchaManager();
    private static int captchaIndex = 0;

    private Captcha currentCaptcha;
    private LinkedList<Captcha> captchasList;

    private CaptchaManager() {
        captchasList = new LinkedList<>();
        getAllCaptchas();
    }

    public static CaptchaManager getInstance() {
        if (captchaManager == null) {
            return new CaptchaManager();
        } else {
            return captchaManager;
        }
    }

    public void getAllCaptchas() {
        Captcha firstCaptcha = new Captcha(5710);
        captchasList.add(firstCaptcha);
        Captcha secondCaptcha = new Captcha(7447);
        captchasList.add(secondCaptcha);
        Captcha thirdCaptcha = new Captcha(8843);
        captchasList.add(thirdCaptcha);
        Captcha fourthCaptcha = new Captcha(8947);
        captchasList.add(fourthCaptcha);
        Captcha fifthCaptcha = new Captcha(9125);
        captchasList.add(fifthCaptcha);
    }

    public Image getCaptchaImage() {
        int currentIndex = captchaIndex % 5;
        captchaIndex++;
        currentCaptcha = captchasList.get(currentIndex);
        return currentCaptcha.getCaptchaImage();
    }

    public Captcha getCurrentCaptcha() {
        return currentCaptcha;
    }
}
