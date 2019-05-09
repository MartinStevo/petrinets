package gui;

import Exceptions.MissingObjectException;
import Exceptions.UnfireableTransitionException;
import javafx.scene.layout.Pane;

public interface Drawable extends NetClickListener {

     void draw(Pane pane, double coefX, double coefY, NetUpdateListener netUpdateListener) throws UnfireableTransitionException, MissingObjectException;

     long getId();

     short getX();

     short getY();
}
