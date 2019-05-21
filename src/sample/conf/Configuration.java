/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.conf;

/**
 *
 * @author Gio
 */
public class Configuration {
    private double width = Const.PANE_WIDTH;
    private double height = Const.PANE_HEIGHT;
    private double opacity = 1.0;
    private String theme = Const.WIN;
    private boolean alwaysOnTop = false;
    

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    
    public double getOpacity() {
        return height;
    }

    public String getTheme() {
        return theme;
    }
    
    public boolean isAlwaysOnTop() {
            return alwaysOnTop;
        }
    
    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }
    
}
