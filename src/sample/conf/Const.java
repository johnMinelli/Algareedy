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
    
    public static final String WIN = "WIN";
    public static final String MAC = "MAC";
    public static final EventType<Event> EVENT_ALL = new EventType<>("EVENT_ALL");
    public static final EventType<Event> EVENT_APPLY_THEME = new EventType<>(EVENT_ALL, "EVENT_APPLY_THEME");
        
}


