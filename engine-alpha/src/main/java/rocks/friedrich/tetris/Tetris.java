package rocks.friedrich.tetris;

import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.tetris.screens.CopyrightScreen;
import rocks.friedrich.engine_omega.Game;

public class Tetris {

    /**
     * Die Größe eines Blocks in Pixeln. In dem klassischen Gameboy-Tetris
     * hat ein Block die Größe {@code 8x8}, die Blockgröße beträgt also {@code 8}
     * Pixel.
     */
    public static final int BLOCK_SIZE = 8;

    /**
     * Die Breite des Spielfelds in Blöcken. In dem klassischen Gameboy-Tetris
     * passen 20 Blöcken in die Breite.
     */
    public static final int WIDTH = 20;

    /**
     * Die Höhe des Spielfelds in Blöcken. In dem klassischen Gameboy-Tetris
     * passen 18 Blöcken in die Höhe.
     */
    public static final int HEIGHT = 18;

    /**
     * Die Skalierung des Spiels. Wird zum Beispiel 4 eingestellt, so wird ein
     * {@code 8x8} Block auf {@code 32x32} vergrößert.
     */
    public static final int SCALE = 4;

    /**
     * Schaltet den Entwicklermodus ein oder aus. Im Entwicklermodus werden
     * zum Beispiel statt den normalen Blockbilder Zahlen angezeigt.
     */
    public static boolean DEBUG = false;

    public static void start(Scene scene, boolean debug) {
        scene.getCamera().setZoom(Tetris.SCALE * Tetris.BLOCK_SIZE);
        Game.setDebug(debug);
        Game.start(BLOCK_SIZE * WIDTH * SCALE, BLOCK_SIZE * HEIGHT * SCALE, scene);
    }

    public static void start(Scene scene) {
        start(scene, false);
    }

    public static void main(String[] args) {
        Scene scene = new CopyrightScreen();
        Game.start(BLOCK_SIZE * WIDTH * SCALE, BLOCK_SIZE * HEIGHT * SCALE, scene);
    }
}
