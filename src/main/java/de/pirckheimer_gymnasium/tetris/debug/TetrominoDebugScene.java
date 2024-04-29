package de.pirckheimer_gymnasium.tetris.debug;

import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.event.KeyListener;
import de.pirckheimer_gymnasium.tetris.Tetris;
import de.pirckheimer_gymnasium.tetris.tetrominos.*;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class TetrominoDebugScene extends Scene implements KeyListener
{
    private boolean DEBUG = true;

    private Text rotation;

    Tetromino[] t;

    public TetrominoDebugScene()
    {
        rotation = new Text("0", 2);
        rotation.setColor(Color.WHITE);
        rotation.setPosition(-7, 0);
        add(rotation);
        t = new Tetromino[7];
        t[0] = createTetromino("L", -5, 5);
        t[1] = createTetromino("J", 0, 5);
        t[2] = createTetromino("I", 5, 5);
        t[3] = createTetromino("O", 0, 0);
        t[4] = createTetromino("Z", -5, -5);
        t[5] = createTetromino("S", 0, -5);
        t[6] = createTetromino("T", 5, -5);
    }

    private Tetromino createTetromino(String name, int x, int y)
    {
        switch (name)
        {
        case "L":
            return new L(this, x, y, DEBUG);

        case "J":
            return new J(this, x, y, DEBUG);

        case "I":
            return new I(this, x, y, DEBUG);

        case "O":
            return new O(this, x, y, DEBUG);

        case "Z":
            return new Z(this, x, y, DEBUG);

        case "S":
            return new S(this, x, y, DEBUG);

        case "T":
            return new T(this, x, y, DEBUG);

        default:
            return new L(this, x, y, DEBUG);
        }
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_LEFT:
            for (Tetromino i : t)
            {
                i.moveLeft();
            }
            break;

        case KeyEvent.VK_RIGHT:
            for (Tetromino i : t)
            {
                i.moveRight();
            }
            break;

        case KeyEvent.VK_DOWN:
            for (Tetromino i : t)
            {
                i.moveDown();
            }
            break;

        case KeyEvent.VK_SPACE:
            for (Tetromino i : t)
            {
                i.rotate();
            }
            rotation.setContent(String.valueOf(t[0].rotation));
            break;
        }
    }

    public static void main(String[] args)
    {
        Scene scene = new TetrominoDebugScene();
        Tetris.start(scene, true);
    }
}
