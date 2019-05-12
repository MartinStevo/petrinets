package gui;

import Exceptions.MissingObjectException;
import Exceptions.UnfireableTransitionException;
import Exceptions.WrongArcException;
import Exceptions.WrongValueException;
import components.Place;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Place2D implements Drawable {

    private short x;
    private short y;
    private Place place;
    private Circle circle;
    private Text text;
    private Text textPlace;
    private Long id;

    public Place2D(Long id, short x, short y, Place place) {
        this.x = x;
        this.y = y;
        this.place = place;
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
        return place.getId();
    }

    public Place getPlace() {
        return place;
    }

    @Override
    public void draw(Pane pane, double coefX, double coefY, NetUpdateListener netUpdateListener) {
        circle = new Circle(x * coefX, y * coefY, 20);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseClicked(event -> {
            try {
                click(netUpdateListener);
            } catch (WrongValueException | WrongArcException | MissingObjectException e) {
                e.printStackTrace();
            }
        });
        pane.getChildren().add(circle);

        text = new Text();
        text.setText(String.valueOf(place.getValue()));
        text.setX(x * coefX - 3);
        text.setY(y * coefY);
        text.setFill(Color.BLACK);
        pane.getChildren().add(text);

        textPlace = new Text();
        textPlace.setText(String.valueOf(place.getName()));
        textPlace.setX(x * coefX - 2);
        textPlace.setY(y * coefY + 30);
        textPlace.setFill(Color.BLACK);
        textPlace.setFont(Font.font(10));
        pane.getChildren().add(textPlace);
    }

    @Override
    public void click(NetUpdateListener netUpdateListener) throws WrongValueException, WrongArcException, MissingObjectException {
        switch(netUpdateListener.getSelectedEventType()){
            case ADD_TOKEN: {
                place.setValue(place.getValue() + 1);
                break;
            }
            case REMOVE_TOKEN: {
                if (place.getValue() != 0) {
                    place.setValue(place.getValue() - 1);
                    break;
                }
            }
            case ADD_ARC_START: {
                netUpdateListener.createArcStart(place.getId());
                break;
            }
            case ADD_ARC_END: {
                netUpdateListener.createArcEnd(place.getId());
                break;
            }
            case ADD_RESET_ARC_START: {
                netUpdateListener.createResetArcStart(place.getId());
                break;
            }
            case ADD_RESET_ARC_END: {
                netUpdateListener.createResetArcEnd(place.getId());
                break;
            }
            case DELETE: {
                netUpdateListener.deleteDrawable(getId());
                break;
            }

        }
        netUpdateListener.setOnClickedId(place.getId());
        netUpdateListener.updateNet();
    }
}
