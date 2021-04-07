/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.ArrayList;

/**
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public interface Component {
    public void operation();
    public void operation(String command);
    public void add(Component component);
    public void remove(Component component);
    public ArrayList<Component> getChildren();
}
