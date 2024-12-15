package src;

import java.io.IOException;

import src.GameExceptions.GameException;

public class App
{
public static void main ( String [] args ) throws GameException, IOException
{
    Game g = new Game () ;
    g . launch () ;
}
}
