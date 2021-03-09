/*
 * What should set my product (brand) apart:
 *  Identify
 *  Beliefs
 *  Knowing what you stand for
 *  Connections
 *  Resisting Outdated Industry Norms
 */
package Root;

import State.AnimDevspaceAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * 
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 * @version 0.1
 * @since 0.1
 */
public class ApplicationShell extends SimpleApplication implements ScreenController{
    
    Nifty nifty;
    AnimDevspaceAppState workspace;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ApplicationShell app = new ApplicationShell();
        
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
        nifty.fromXml("Interface/EditorScreens.xml", "GScreen1", this);

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);

        // disable the fly cam
        // Cam by default is at (0,0,10), looking at -z
        cam.setLocation(new Vector3f(0,3,10));
        cam.lookAt(new Vector3f(0,3,0), Vector3f.UNIT_Y);
        //flyCam.setEnabled(false);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        
        inputManager.clearMappings();
        
        workspace = new AnimDevspaceAppState();
        viewPort.attachScene(workspace.getRootNode());
        stateManager.attach(workspace);
        
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
    }
    
    private void getCamData(){
        Vector3f camLocation = cam.getLocation();
        Quaternion camRotation = cam.getRotation();
        cam.getLeft();
        cam.getUp();
        cam.getDirection();
    }
    
    private void setCamData(){
        cam.setLocation(Vector3f.NAN);
        cam.setRotation(Quaternion.IDENTITY);
        cam.setAxes(Vector3f.ZERO, Vector3f.NAN, Vector3f.ZERO);
        cam.resize(0, 0, paused);
        cam.lookAt(Vector3f.NAN, Vector3f.ZERO);
        cam.lookAtDirection(Vector3f.ZERO, Vector3f.NAN);
    }
    
    float[] angles = new float[3];
    
    @Override
    public void simpleUpdate(float tpf){
        /** Each frame, we need to grab the cam pos from workspace and smoothly translate there **/
        Vector3f camPosTarget = workspace.getCamPosTarget();
        Vector3f subtract = camPosTarget.subtract(cam.getLocation());
        if(subtract.length() > 0.001f){
            cam.setLocation(camPosTarget.subtract(subtract.mult(0.1f)));
            cam.lookAt(workspace.getCamLookTarget(), Vector3f.UNIT_Y);
        }
        
    }
    
    // Nifty element members
    Screen currentScreen;

    @Override
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("Nifty Bind Screen: [ScreenId = " + screen.getScreenId() + " ]");
        currentScreen = screen;
    }

    @Override
    public void onStartScreen() {
        System.out.println("Nifty Start Screen: [ScreenId = " + currentScreen.getScreenId() + " ]");
    }

    @Override
    public void onEndScreen() {
        System.out.println("Nifty End Screen: [ScreenId = " + currentScreen.getScreenId() + " ]");
    }
    
    @Override
    public void destroy(){
        
    }
    
}
