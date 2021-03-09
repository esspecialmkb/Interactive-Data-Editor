/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIController;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.List;

/**
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class AnimDevspaceController implements ScreenController{
    // CurrentBone string keeps track of what bone should be selected
    static String currentBone = "",nextBone = "";
    static boolean newBone = false, rotBone = false;

    // Nifty element members
    static Screen currentScreen;
    static Slider xslider;
    static Slider yslider;
    static Slider zslider;
    static Label xlabel;
    static Label ylabel;
    static Label zlabel;

    @Override
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("Nifty Bind Screen: [ScreenId = " + screen.getScreenId() + " ]");
        currentScreen = screen;
        
        xslider = screen.findNiftyControl("sliderX", Slider.class);
        xslider.setMin(-180);
        xslider.setMax(180);
        xslider.setStepSize(1.0f);
        xslider.setValue(0f);
        
        yslider = screen.findNiftyControl("sliderY", Slider.class);
        yslider.setMin(-180);
        yslider.setMax(180);
        yslider.setStepSize(1.0f);
        yslider.setValue(0f);
        
        zslider = screen.findNiftyControl("sliderZ", Slider.class);
        zslider.setMin(-180);
        zslider.setMax(180);
        zslider.setStepSize(1.0f);
        zslider.setValue(0f);
        
        xlabel = screen.findNiftyControl("labelX", Label.class);
        ylabel = screen.findNiftyControl("labelY", Label.class);
        zlabel = screen.findNiftyControl("labelZ", Label.class);
        
        xlabel.setText("X: 0");
        ylabel.setText("Y: 0");
        zlabel.setText("Z: 0");
        
        ListBox listBox = screen.findNiftyControl("myListBox", ListBox.class);
        if(listBox != null){
            listBox.addItem("Root");
            
            /** Pelvis, child of Root **/
            listBox.addItem("Pelvis");
            
            /** Hip.L, child of Pelvis **/
            listBox.addItem("Hip.L");
            
            listBox.addItem("Thigh.L");
            listBox.addItem("Shin.L");
            listBox.addItem("Foot.L");
            listBox.addItem("Toe.L");
            
            /** Hip.R, child of Pelvis **/
            listBox.addItem("Hip.R");
            
            listBox.addItem("Thigh.R");
            listBox.addItem("Shin.R");
            listBox.addItem("Foot.R");
            listBox.addItem("Toe.R");
            
            /** Waist, child of Pelvis **/
            listBox.addItem("Waist");
            
            /** Torso, child of Waist **/
            listBox.addItem("Torso");
            listBox.addItem("Chest");
            
            listBox.addItem("Shoulder.L");
            listBox.addItem("UArm.L");
            listBox.addItem("FArm.L");
            listBox.addItem("Hand.L");
            
            listBox.addItem("Shoulder.R");
            listBox.addItem("UArm.R");
            listBox.addItem("FArm.R");
            listBox.addItem("Hand.R");
            
            listBox.addItem("Neck");
            listBox.addItem("Head");
        }
        
    }

    @Override
    public void onStartScreen() {
        System.out.println("Nifty Start Screen: [ScreenId = " + currentScreen.getScreenId() + " ]");
    }

    @Override
    public void onEndScreen() {
        System.out.println("Nifty End Screen: [ScreenId = " + currentScreen.getScreenId() + " ]");
    }
    
    @NiftyEventSubscriber(id="myListBox")
    public void onMyListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<String> event) {
        /** Get list of events**/
        List<String> selection = event.getSelection();
        /** Parse the events **/
        for (final String selectedItem : selection) {
            System.out.println("Listbox selection [" + selectedItem + "]");
            nextBone = selectedItem;
            newBone = true;

        }
    }
    
    
    /** Quasi-local slider event storage **/
    static String sliderAxis;
    static float sliderValue;
    
    /**Event Callbacks for sliders, Currently stores data and sets flag for updating later**/
    @NiftyEventSubscriber(id="sliderX")
    public void onSliderXChanged(final String id, final SliderChangedEvent event){
        sliderAxis = "X";
        sliderValue = event.getValue();
        rotBone = true;
    }
    
    @NiftyEventSubscriber(id="sliderY")
    public void onSliderYChanged(final String id, final SliderChangedEvent event){
        sliderAxis = "Y";
        sliderValue = event.getValue();
        rotBone = true;
    }
    
    @NiftyEventSubscriber(id="sliderZ")
    public void onSliderZChanged(final String id, final SliderChangedEvent event){
        sliderAxis = "Z";
        sliderValue = event.getValue();
        rotBone = true;
    }
    
    
    /** Quasi-local bone data storage **/
    Vector3f posData;
    Quaternion rotData;
    
}
