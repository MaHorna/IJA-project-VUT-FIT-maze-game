/**
 * @author Matej Horňanský
 *
 * Utility class
 */

package com.pacman;

import javafx.scene.image.ImageView;

/**
 * Utility class
 */
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
