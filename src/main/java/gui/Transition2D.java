package gui;

import Exceptions.MissingObjectException;
import Exceptions.UnfireableTransitionException;
import Exceptions.WrongArcException;
import Exceptions.WrongValueException;
import components.Arc;
import components.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class Transition2D implements Drawable {

    private short x;
    private short y;
    private Transition transition;
    private Rectangle rectangle;
    private Text text;

    public Transition2D(Long id, short x, short y, Transition transition) {
        this.x = x;
        this.y = y;
        this.transition = transition;
    }

    @Override
    public short getX() {
        return x;
    }

    @Override
    public short getY() {
        return y;
    }

    @Override
    public long getId() {
        return transition.getId();
    }

    public Transition getTransition() {
        return transition;
    }

    @Override
    public void draw(Pane pane, double coefX, double coefY, NetUpdateListener netUpdateListener) throws UnfireableTransitionException, MissingObjectException {
        rectangle = new Rectangle(x * coefX - 15, y * coefY - 15, 30, 30);
        rectangle.setStroke(Color.BLACK);
        if (transition.isValidTransition()) rectangle.setFill(Color.GREEN);
        else rectangle.setFill(Color.RED);

        rectangle.setOnMouseClicked(event -> {
            try {
                click(netUpdateListener);
            } catch (WrongValueException | WrongArcException | MissingObjectException | UnfireableTransitionException e) {
                e.printStackTrace();
            }
        });

        pane.getChildren().add(rectangle);

        text = new Text();
        text.setText(String.valueOf(transition.getName()));
        text.setX(x * coefX - 2);
        text.setY(y * coefY + 30);
        text.setFill(Color.BLACK);
        text.setFont(Font.font(10));
        pane.getChildren().add(text);
    }

    private void setTokens() {

    }

    private List<Arc> getArcList(List<Arc> arcList) {
        arcList.addAll(transition.placeToTransitionArcList);
        arcList.addAll(transition.placeToTransitionArcList);
        arcList.addAll(transition.resetArcList);
        return  arcList;
    }

    @Override
    public void click(NetUpdateListener netUpdateListener) throws WrongArcException, MissingObjectException, WrongValueException, UnfireableTransitionException {
        switch(netUpdateListener.getSelectedEventType()){
            case ADD_ARC_START: {
                netUpdateListener.createArcStart(transition.getId());
                break;
            }
            case ADD_ARC_END: {
                netUpdateListener.createArcEnd(transition.getId());
                break;
            }
            case FIRE: {
                transition.fire();
                if (!transition.isValidTransition()) {
                    rectangle.setFill(Color.RED);
                }
                break;
            }
            case DELETE: {
                netUpdateListener.deleteDrawable(getId());
                break;
            }
        }

        netUpdateListener.setOnClickedId(transition.getId());
        netUpdateListener.updateNet();
    }


}

/*

 */