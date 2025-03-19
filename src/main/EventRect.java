package main;

import java.awt.*;

public class EventRect extends Rectangle {
    // coordonatele implicite ale EventRect
    int eventRectDefaultX, eventRectDefaultY;
    // indica daca evenimentul a fost realizat sau nu
    boolean eventDone =  false;
}
