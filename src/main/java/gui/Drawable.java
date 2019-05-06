package gui;

import Exceptions.MissingObjectException;
import Exceptions.UnfireableTransitionException;
import javafx.scene.layout.Pane;

public interface Drawable {

     void draw(Pane pane, double coefX, double coefY, NetUpdateListener netUpdateListener) throws UnfireableTransitionException, MissingObjectException;

}
