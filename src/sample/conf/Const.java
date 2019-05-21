/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.conf;

import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Gio
 */
public class Const {
    public static final String FILENAME = "a.xml";
    public static final int PANE_WIDTH = 650;
    public static final int PANE_HEIGHT = 500;
    public static final int BAR_WIN_WIDTH = 14;
    public static final int BAR_MAC_WIDTH = 81;
    public static final String WIN = "WIN";
    public static final String MAC = "MAC";
    public static final int ANY = 0;
    public static final int LESSON = 1;
    public static final int QUIZ = 2;
    public static final EventType<Event> EVENT_ALL = new EventType<>("EVENT_ALL");
    public static final EventType<Event> EVENT_APPLY_THEME = new EventType<>(EVENT_ALL, "EVENT_APPLY_THEME");
    public static final EventType<Event> EVENT_ANY_BEHAVIOUR = new EventType<>(EVENT_ALL, "EVENT_ANY_BEHAVIOUR");
    public static final EventType<Event> EVENT_LESSON_BEHAVIOUR = new EventType<>(EVENT_ALL, "EVENT_LESSON_BEHAVIOUR");
    public static final EventType<Event> EVENT_QUIZ_BEHAVIOUR = new EventType<>(EVENT_ALL, "EVENT_QUIZ_BEHAVIOUR");
    public static final EventType<Event> EVENT_OPEN_LESSON = new EventType<>(EVENT_ALL, "EVENT_OPEN_LESSON");
    public static final EventType<Event> EVENT_OPEN_QUIZ = new EventType<>(EVENT_ALL, "EVENT_OPEN_QUIZ");

}


