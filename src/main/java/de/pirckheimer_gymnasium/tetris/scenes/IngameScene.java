package de.pirckheimer_gymnasium.tetris.scenes;

import java.awt.event.KeyEvent;
import java.util.Random;

import de.pirckheimer_gymnasium.tetris.Tetris;
import de.pirckheimer_gymnasium.tetris.tetrominos.Grid;
import de.pirckheimer_gymnasium.tetris.tetrominos.Tetromino;
import de.pirckheimer_gymnasium.tetris.text.NumberDisplay;
import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.event.KeyListener;
import rocks.friedrich.engine_omega.event.PeriodicTask;

public class IngameScene extends BaseScene implements KeyListener
{
    private Grid grid;

    /**
     * Der Zufallsgenerator wird benötigt, um zufällig neue Tetrominos zu
     * erzeugen. Wir verwenden die Method nextInt() um zufällig Zahlen von 0 bis
     * einschließlich 6 zu bekommen.
     *
     * @see #createNextTetromino()
     */
    private static Random random = new Random();

    /**
     * Die Nummer des nächsten Tetrominos.
     */
    private int nextTetromino;

    /**
     * Das aktuelle Tetromino, das gesteuert werden kann und automatisch nach
     * unten fällt.
     */
    private Tetromino tetromino;

    /**
     * Das Vorschaubild des nächsten Tetrominos im linken unteren Bereich.
     */
    private Tetromino previewTetromino;

    private NumberDisplay score;

    private NumberDisplay level;

    private NumberDisplay clearedLines;

    /**
     * Nach wie vielen Frames ein Tetromino eine Reihe weiter nach unten gesetzt
     * wird und zwar im Verhältnis zur Bildwiederholungsrate des originalen
     * Gameboys {@code 59.73}.
     *
     * Quelle: <a href=
     * "https://harddrop.com/wiki/Tetris_%28Game_Boy%29">harddrop.com</a>
     */
    private final int[] GB_FRAMES_PER_ROM = new int[] { 53, 49, 45, 41, 37, 33,
            28, 22, 17, 11, 10, 9, 8, 7, 6, 6, 5, 5, 4, 4, 3 };

    /**
     * Die Bildwiederholungsrate des originalen Gameboys pro Sekunde.
     *
     * Quelle: <a href=
     * "https://harddrop.com/wiki/Tetris_%28Game_Boy%29">harddrop.com</a>
     */
    private final double GB_FRAME_RATE = 59.73;

    protected PressedKeyRepeater<Scene> keyRepeater;

    PeriodicTask periodicTask;

    /**
     * Gibt an, ob sich das Tetromino in einer Soft-Drop-Bewegung befindet. Als
     * Soft-Drop bezeichnet man die schnellere nach unten gerichtete Bewegung
     * des Tetromino.
     */
    private boolean isInSoftDrop = false;

    public IngameScene()
    {
        super("ingame");
        // Das I-Tetromino ragt einen Block über das sichtbare Spielfeld hinaus,
        // wenn es in der Startposition gedreht wird, deshalb machen wir das
        // Blockgitter um eine Zeile höher.
        grid = new Grid(Tetris.GRID_WIDTH, Tetris.HEIGHT + 1);
        createNextTetromino();
        score = new NumberDisplay(this, 13, 14, 4);
        level = new NumberDisplay(this, 13, 10, 4);
        clearedLines = new NumberDisplay(this, 13, 7, 4);
        periodicTask = repeat(caculateDownInterval(), () -> {
            if (!isInSoftDrop)
            {
                moveDown();
            }
        });
        keyRepeater = new PressedKeyRepeater<Scene>(this);
        keyRepeater.addTask(KeyEvent.VK_DOWN, () -> {
            isInSoftDrop = true;
            moveDown();
        }, () -> {
            isInSoftDrop = false;
        });
        keyRepeater.addTask(KeyEvent.VK_RIGHT, () -> tetromino.moveRight());
        keyRepeater.addTask(KeyEvent.VK_LEFT, () -> tetromino.moveLeft());
    }

    private void createNextTetromino()
    {
        // Beim ersten Mal müssen zwei zufällige Tetrominos erzeugt werden.
        // Wir müssen also zweimal eine Zufallszahl generieren.
        if (previewTetromino == null)
        {
            nextTetromino = random.nextInt(7);
        }
        tetromino = Tetromino.create(this, grid, nextTetromino, 4, 16);
        nextTetromino = random.nextInt(7);
        // Entfernen des alten Vorschaubildes, falls vorhanden.
        if (previewTetromino != null)
        {
            previewTetromino.remove();
        }
        // Das Vorschaubild liegt außerhalb des Blockgitters. Wir übergeben der
        // Methode null.
        previewTetromino = Tetromino.create(this, null, nextTetromino, 14, 3);
    }

    /**
     * https://tetris.wiki/Scoring
     */
    private int caculateScore(int clearedLines)
    {
        int score = 40;
        if (clearedLines == 2)
        {
            score = 100;
        }
        else if (clearedLines == 3)
        {
            score = 300;
        }
        else if (clearedLines == 4)
        {
            score = 1200;
        }
        return score * (level.get() + 1);
    }

    /**
     * Berechnet das Zeitintervall in Sekunden, wie lange es dauert, bis sich
     * das aktuelle Tetromino von einer Reihe zur darunterliegenden Reihe
     * bewegt.
     */
    private double caculateDownInterval()
    {
        return 1.0 / GB_FRAME_RATE * GB_FRAMES_PER_ROM[level.get()];
    }

    private void moveDown()
    {
        if (!tetromino.moveDown())
        {
            var range = grid.getFilledRowRange();
            if (range != null)
            {
                grid.removeFilledRowRange(range);
                grid.triggerLandslide(range);
                clearedLines.add(range.getRowCount());
                score.add(3);
            }
            createNextTetromino();
        }
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_SPACE:
            tetromino.rotate();
            break;
        }
        if (Game.isDebug())
        {
            grid.print();
        }
    }

    public static void main(String[] args)
    {
        Tetris.start(new IngameScene());
    }
}
