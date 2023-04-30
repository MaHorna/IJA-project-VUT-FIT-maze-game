/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Utility class for replay
 */
package com.example;

public class replay_holder {
    public replay replay;
    private final static replay_holder INSTANCE = new replay_holder();
    private replay_holder() {}
    public static replay_holder get_instance() {
        return INSTANCE;
    }
    public void set_replay(replay r) {
        this.replay = r;
    }
    public replay get_replay() {
        return this.replay;
    }
}