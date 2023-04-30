/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Utility class
 */

package com.example;

import javafx.scene.image.ImageView;

public class triple {
    public int x;
    public int y;
    public boolean key_is_taken;
    public ImageView image_view;

    public triple(int x, int y, boolean key_is_taken) {
        this.x = x;
        this.y = y;
        this.key_is_taken = key_is_taken;
    }
}
