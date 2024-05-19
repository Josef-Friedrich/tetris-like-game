package de.pirckheimer_gymnasium.tetris.debug;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.tetris.Tetris;
import de.pirckheimer_gymnasium.tetris.text.TextField;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.event.KeyListener;

public class TextFieldDebugScene extends Scene implements KeyListener
{
    public TextFieldDebugScene()
    {
        TextField textField = new TextField(this, 0, 8, 5, 20);
        getCamera().setPostion(10, 8);

        textField.write("Hello, World. Lorem ipsum. Lorem ipsum. Lorem ipsum","#ff0000");
        setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_L:
            break;
        }
    }

    public static void main(String[] args)
    {
        Tetris.start(new TextFieldDebugScene(), true);
    }
}
