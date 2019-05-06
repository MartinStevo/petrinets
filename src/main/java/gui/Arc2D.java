package gui;

import components.Arc;
import components.ResetArc;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Arc2D implements Drawable {

    private short sourceX;
    private short sourceY;
    private short destinationX;
    private short destinationY;
    private int value;
    private Arc arc;
    private Line line;

    public Arc2D(short sourceX, short sourceY, short destinationX, short destinationY, int value, Arc arc) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.value = value;
        this.arc = arc;
    }

    @Override
    public void draw(Pane pane, double coefX, double coefY, NetUpdateListener netUpdateListener) {
        line = new Line(sourceX * coefX, sourceY * coefY, destinationX * coefX, destinationY * coefY);
        line.setFill(Color.BLACK);
        pane.getChildren().add(line);
        drawTriangle(sourceX * coefX, sourceY * coefY, destinationX * coefX, destinationY * coefY, pane);
    }

    private void drawTriangle(double startX, double startY, double endX, double endY, Pane pane) {

        Polygon polygon = new Polygon();
        Double angle = getAngle(startX, startY, endX, endY);
        Double paramX = (endX - startX) / 8;
        Double paramY = (endY - startY) / 8;

        if (!(arc instanceof ResetArc)) {
            polygon.getPoints().addAll(
                    ((startX + endX) / 2) + paramX, ((startY + endY) / 2) - 4.4 + paramY,
                    ((startX + endX) / 2) + 5 + paramX, ((startY + endY) / 2) + 4.4 + paramY,
                    ((startX + endX) / 2) - 5 + paramX, ((startY + endY) / 2) + 4.4 + paramY

            );
            drawMultiplicity(((startX + endX) / 2) + paramX - 5, ((startY + endY) / 2) + paramY - 10, pane);
        } else {
            polygon.getPoints().addAll(
                    ((startX + endX) / 2), ((startY + endY) / 2) - 8.8,
                    ((startX + endX) / 2) + 5, ((startY + endY) / 2),
                    ((startX + endX) / 2), ((startY + endY) / 2),
                    ((startX + endX) / 2) + 5, ((startY + endY) / 2) + 8.8,
                    ((startX + endX) / 2) - 5, ((startY + endY) / 2) + 8.8,
                    ((startX + endX) / 2), ((startY + endY) / 2),
                    ((startX + endX) / 2) - 5, ((startY + endY) / 2)
            );
        }

        polygon.setRotate(angle);
        pane.getChildren().add(polygon);
    }

    private double getAngle(double startX, double startY, double endX, double endY) {
        double xDiff = endX - startX;
        double yDiff = endY - startY;
        return Math.toDegrees(Math.atan2(yDiff, xDiff)) + 90;
    }

    private void drawMultiplicity(double X, double Y, Pane pane) {
        Text text = new Text();
        text.setText(String.valueOf(value));
        text.setX(X);
        text.setY(Y);
        text.setFill(Color.BLACK);
        text.setFont(Font.font(10));
        pane.getChildren().add(text);
    }
}
