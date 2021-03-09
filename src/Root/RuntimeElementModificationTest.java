/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Root;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.dynamic.CustomControlCreator;
import de.lessvoid.nifty.controls.dynamic.ImageCreator;
import de.lessvoid.nifty.controls.dynamic.LayerCreator;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.controls.dynamic.PopupCreator;
import de.lessvoid.nifty.controls.dynamic.ScreenCreator;
import de.lessvoid.nifty.controls.dynamic.TextCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.loaderv2.types.ControlType;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class RuntimeElementModificationTest extends SimpleApplication implements ScreenController{
    Nifty nifty;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RuntimeElementModificationTest app = new RuntimeElementModificationTest();
        
        AppSettings settings = new AppSettings(false);
        settings.setTitle("Application Shell");
        settings.setResizable(true);
        
        app.setSettings(settings);
        app.setPauseOnLostFocus(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/BaseScreen.xml", "start", this);

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        //Screen screen = nifty.getCurrentScreen();
        Element layer = screen.findElementById("baseLayer");
        
        // Create an 8px height red panel
        PanelCreator createPanel = new PanelCreator();
        createPanel.setHeight("8px");
        createPanel.setBackgroundColor("#f00f");
        Element newPanel = createPanel.create(nifty,screen,layer);
        
        // Alternate Panel Builder
        new PanelBuilder() {{
            height("8px");
            backgroundColor("#f00f");
        }}.build(nifty, screen, layer);
        
        // Create a new control instance. This is the same as the <control> tag in XML
        //CustomControlCreator customControl = new CustomControlCreator();
        
        // Craete a new image element
        ImageCreator createImage = new ImageCreator();
        
        // Create a new layer
        LayerCreator createLayer = new LayerCreator();
        
        // Create a popup element
        PopupCreator createPopup = new PopupCreator();
        
        // Create a new screen
        ScreenCreator createScreen = new ScreenCreator("Root.RuntimeElementModificationTest");
        
        // Create a new text element
        TextCreator createText = new TextCreator("New Text");
    }

    @Override
    public void onStartScreen() {
        
    }

    @Override
    public void onEndScreen() {
        
    }
    
}
