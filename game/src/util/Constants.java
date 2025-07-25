package util;

import java.awt.Toolkit;

public class Constants {

    public final static String TITLE = "GAME_NAME";
    public final static String PLAY = "Press 'E' to Play!";
    public final static String EXIT = "Quit";
    public final static int SCREEN_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    public final static int SCREEN_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());;
    public final static int GAME_FPS = 60;
    public final static int TILE_SIZE = 64;
}
