package storage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadHUD {
    public static String HUDLEFT = "hudleft.gif";
    public static String HUDRIGHT = "hudright.gif";

    public static BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(new File("D:/Assignments/Project-OOP/Disanour_Tale/src/resources/hud/"+name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}