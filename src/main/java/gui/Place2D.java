package gui;

import components.Place;
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

    public Place2D(short x, short y, Place place) {
        this.x = x;
        this.y = y;
        this.place = place;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public Place2D() {

    }

    @Override
    public void draw(Pane pane, double coefX, double coefY, NetUpdateListener netUpdateListener) {
        circle = new Circle(x * coefX, y * coefY, 20);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
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
}
