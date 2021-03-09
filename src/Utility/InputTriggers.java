/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;

/**
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class InputTriggers {
    public final static Trigger TRIGGER_F1 = new KeyTrigger(KeyInput.KEY_F1);
    
    public final static Trigger TRIGGER_1 = new KeyTrigger(KeyInput.KEY_1);
    
    public final static Trigger TRIGGER_N1 = new KeyTrigger(KeyInput.KEY_NUMPAD1);
    
    public final static Trigger TRIGGER_A = new KeyTrigger(KeyInput.KEY_A);
    
    public final static Trigger TRIGGER_SPACEBAR = new KeyTrigger(KeyInput.KEY_SPACE);
    
    public final static Trigger TRIGGER_DOWN = new KeyTrigger(KeyInput.KEY_DOWN);
    public final static Trigger TRIGGER_UP = new KeyTrigger(KeyInput.KEY_UP);
    public final static Trigger TRIGGER_LEFT = new KeyTrigger(KeyInput.KEY_LEFT);
    public final static Trigger TRIGGER_RIGHT = new KeyTrigger(KeyInput.KEY_RIGHT);
    
    public final static String MAP_INCREASE_Z_SAMPLES = "Increase Z Samples";
    
    //  Example input mapping usage
    public static void assignMapping(InputManager inputManager){
        inputManager.addMapping(MAP_INCREASE_Z_SAMPLES, TRIGGER_F1);
    }
    
    //  Example actionListener usage
    public static void assignListener(InputManager inputManager, ActionListener actionListener){
        inputManager.addListener(actionListener, MAP_INCREASE_Z_SAMPLES);
    }
    
}
