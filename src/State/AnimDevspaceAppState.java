/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import com.jme3.animation.AnimControl;
import com.jme3.animation.Bone;
import com.jme3.animation.Skeleton;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.controls.TreeItem;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *  This AppState is intended to serve as the 'Workspace'
 * 
 *  The workspace contains a view of the 3d object or scene that is currently being edited
 * 
 *  This workspace will emulate a "3d editor" environment 
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com> 
 */
public class AnimDevspaceAppState extends AbstractAppState implements ScreenController{
    
    Application app;
    
    // This root node will be the parent of the animation scene
    private Node rootNode = new Node("Root Node");
    
    // These nodes will help with the camera controls
    // Note - This could be encapsulated in a jME Control
    private Node camRootNode = new Node("Cam Root Node");
    private Node camTargetNodeH = new Node("Cam Target H Node");
    private Node camTargetNodeV = new Node("Cam Target V Node");
    private Node camPosNode = new Node("Cam Position Node");

    // Input Triggers
    public final static MouseButtonTrigger L_MOUSE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    public final static MouseButtonTrigger M_MOUSE = new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE);
    public final static MouseButtonTrigger R_MOUSE = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
    
    public final static Trigger X_MOUSE_R = new MouseAxisTrigger(MouseInput.AXIS_X, true);
    public final static Trigger X_MOUSE_L = new MouseAxisTrigger(MouseInput.AXIS_X, false);
    public final static Trigger Y_MOUSE_U = new MouseAxisTrigger(MouseInput.AXIS_Y, true);
    public final static Trigger Y_MOUSE_D = new MouseAxisTrigger(MouseInput.AXIS_Y, false);
    
    public final static Trigger W_MOUSE_U = new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false);
    public final static Trigger W_MOUSE_D = new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true);
    
    public final static Trigger KEY_0 = new KeyTrigger(KeyInput.KEY_0);
    public final static Trigger KEY_1 = new KeyTrigger(KeyInput.KEY_1);
    public final static Trigger KEY_2 = new KeyTrigger(KeyInput.KEY_2);
    public final static Trigger KEY_3 = new KeyTrigger(KeyInput.KEY_3);
    public final static Trigger KEY_4 = new KeyTrigger(KeyInput.KEY_4);
    public final static Trigger KEY_5 = new KeyTrigger(KeyInput.KEY_5);
    public final static Trigger KEY_6 = new KeyTrigger(KeyInput.KEY_6);
    public final static Trigger KEY_7 = new KeyTrigger(KeyInput.KEY_7);
    public final static Trigger KEY_8 = new KeyTrigger(KeyInput.KEY_8);
    public final static Trigger KEY_9 = new KeyTrigger(KeyInput.KEY_9);
    
    public final static Trigger KEY_F0 = new KeyTrigger(KeyInput.KEY_0);
    public final static Trigger KEY_F1 = new KeyTrigger(KeyInput.KEY_F1);
    public final static Trigger KEY_F2 = new KeyTrigger(KeyInput.KEY_F2);
    public final static Trigger KEY_F3 = new KeyTrigger(KeyInput.KEY_F3);
    public final static Trigger KEY_F4 = new KeyTrigger(KeyInput.KEY_F4);
    public final static Trigger KEY_F5 = new KeyTrigger(KeyInput.KEY_F5);
    public final static Trigger KEY_F6 = new KeyTrigger(KeyInput.KEY_F6);
    public final static Trigger KEY_F7 = new KeyTrigger(KeyInput.KEY_F7);
    public final static Trigger KEY_F8 = new KeyTrigger(KeyInput.KEY_F8);
    public final static Trigger KEY_F9 = new KeyTrigger(KeyInput.KEY_F9);
    
    public final static Trigger KEY_SHIFT_L = new KeyTrigger(KeyInput.KEY_LSHIFT);
    public final static Trigger KEY_SHIFT_R = new KeyTrigger(KeyInput.KEY_RSHIFT);
    public final static Trigger KEY_SPACE = new KeyTrigger(KeyInput.KEY_RSHIFT);
    public final static Trigger KEY_LCONTROL = new KeyTrigger(KeyInput.KEY_LCONTROL);
    public final static Trigger KEY_RCONTROL = new KeyTrigger(KeyInput.KEY_RCONTROL);
    
    public final static Trigger KEY_A = new KeyTrigger(KeyInput.KEY_A);
    public final static Trigger KEY_B = new KeyTrigger(KeyInput.KEY_B);
    public final static Trigger KEY_C = new KeyTrigger(KeyInput.KEY_C);
    public final static Trigger KEY_D = new KeyTrigger(KeyInput.KEY_D);
    public final static Trigger KEY_E = new KeyTrigger(KeyInput.KEY_E);
    public final static Trigger KEY_F = new KeyTrigger(KeyInput.KEY_F);
    public final static Trigger KEY_G = new KeyTrigger(KeyInput.KEY_G);
    public final static Trigger KEY_H = new KeyTrigger(KeyInput.KEY_H);
    public final static Trigger KEY_I = new KeyTrigger(KeyInput.KEY_I);
    public final static Trigger KEY_J = new KeyTrigger(KeyInput.KEY_J);
    public final static Trigger KEY_K = new KeyTrigger(KeyInput.KEY_K);
    public final static Trigger KEY_L = new KeyTrigger(KeyInput.KEY_L);
    public final static Trigger KEY_M = new KeyTrigger(KeyInput.KEY_M);
    public final static Trigger KEY_N = new KeyTrigger(KeyInput.KEY_N);
    public final static Trigger KEY_O = new KeyTrigger(KeyInput.KEY_O);
    public final static Trigger KEY_P = new KeyTrigger(KeyInput.KEY_P);
    public final static Trigger KEY_Q = new KeyTrigger(KeyInput.KEY_Q);
    public final static Trigger KEY_R = new KeyTrigger(KeyInput.KEY_R);
    public final static Trigger KEY_S = new KeyTrigger(KeyInput.KEY_S);
    public final static Trigger KEY_T = new KeyTrigger(KeyInput.KEY_T);
    public final static Trigger KEY_U = new KeyTrigger(KeyInput.KEY_U);
    public final static Trigger KEY_V = new KeyTrigger(KeyInput.KEY_V);
    public final static Trigger KEY_W = new KeyTrigger(KeyInput.KEY_W);
    public final static Trigger KEY_X = new KeyTrigger(KeyInput.KEY_X);
    public final static Trigger KEY_Y = new KeyTrigger(KeyInput.KEY_Y);
    public final static Trigger KEY_Z = new KeyTrigger(KeyInput.KEY_Z);    
    
    // Input mappings
    public final static String SELECT = "Select Objects";
    public final static String CAM_TOGGLE = "Camera Toggle";
    public final static String CAM_X = "Increase Z Samples";
    public final static String CAM_X_P = "Camera Right";
    public final static String CAM_X_N = "Camera Left";
    public final static String CAM_Y_P = "Camera Up";
    public final static String CAM_Y_N = "Camera Down";
    public final static String CAM_MOVE_H = "Camera Horiz";
    public final static String CAM_MOVE_V = "Camera Vert";  
    
    // Camera movement toggles
    private boolean camera_toggle = false;
    private boolean camera_move_h = false;
    private boolean camera_move_v = false;
    
    /**Use ActionListener to respond to pressed/released inputs (key presses, mouse clicks) */ 
    private ActionListener actionListener = new ActionListener(){
        @Override
        public void onAction(String name, boolean pressed, float tpf){
            System.out.println(name + " = " + pressed);
            if(SELECT.equals(name)){
                
            }
            if(CAM_TOGGLE.equals(name)){
                camera_toggle = pressed;
            }
            if(CAM_MOVE_H.equals(name)){
                camera_move_h = pressed;
            }
            if(CAM_MOVE_V.equals(name)){
                camera_move_v = pressed;
            }
        }
    };
    /** Use AnalogListener to respond to continuous inputs (key presses, mouse clicks) */
    private AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            //System.out.println(name + " = " + value);
            if(camera_toggle){
                float camRotX = 0;
                float camRotY = 0;
                
                if(CAM_X_N.equals(name)){
                    camRotX -= value*3;
                }
                if(CAM_X_P.equals(name)){
                    camRotX += value*3;
                }
                if(CAM_Y_N.equals(name)){
                    camRotY -= value*3;
                }
                if(CAM_Y_P.equals(name)){
                    camRotY += value*3;
                }
                
                if(camera_move_h){
                    
                }else if(camera_move_v){
                    
                }else{
                    camTargetNodeH.rotate(camRotY, 0, 0);
                    camTargetNodeV.rotate(0,camRotX,0);
                }
            }
        }
    };
    
    
    public Node getRootNode(){
        return rootNode;
    }
    
    public Vector3f getCamPosTarget(){
        return camPosNode.getWorldTranslation();
    }
    
    public Vector3f getCamLookTarget(){
        return camTargetNodeH.getLocalTranslation();
    }
    
    // References to the character object imported from blender
    Node character;
    SkeletonControl charSkelControl;
    Skeleton charSkeleton;
    
    // Array of bones to choose from
    protected Bone[] charBones;
    
    // CurrentBone string keeps track of what bone should be selected
    static String currentBone = "",nextBone = "";
    static boolean newBone = false, rotBone = false;
    Node highlightBone = new Node("Selected Bone Node");
    
    /** We need a way to keep track of what state the editor is in **/
    int EDITOR_MAIN = 1;
    int EDITOR_JOINT_SEL = 2;
    int EDITOR_JOINT_ROTATE = 3;
    
    int editorState;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = app;
        
        /** Create node hierarchy for camera movement **/
        camRootNode.attachChild(camTargetNodeH);
        camTargetNodeH.attachChild(camTargetNodeV);
        camTargetNodeV.attachChild(camPosNode);
        
        // The camRootNode is set to orgin by default and is the root of translation
        camTargetNodeH.setLocalTranslation(0, 3, 0);
        // The camTargetNode is set to the position that the camera should rotate around
        camPosNode.setLocalTranslation(0,0,10);
        // The camPosNode is the position the camera
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
        
        /** Load the model from the blend file **/
        Node loadModel = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/AnimationEditSceneBasic.j3o");
        
        /** Extract the character model from the blend file **/
        Node child = (Node) loadModel.getChild(0); // -> scene
        Node child2 = (Node) child.getChild(1); // -> mesh node
        character = (Node) child2.getChild(0); // -> mesh object (character)
        
        /** Grab the skeleton from the skeleton **/
        System.out.println("Character has " + character.getNumControls() + " controls");
        charSkelControl = character.getControl(SkeletonControl.class);
        AnimControl animControl = character.getControl(AnimControl.class);
        
        
        /** Grab the bones from the skeleton if the skeleton is not = null **/
        if(charSkelControl != null){
            charSkeleton = charSkelControl.getSkeleton();
            
            System.out.println( "Character has " + charSkeleton.getBoneCount() + " bones.");
            
            /** Init Bone Array **/
            charBones = new Bone[charSkeleton.getBoneCount()];
            System.out.println();
            
            /** Populate Bone Array **/
            for(int b = 0; b < charSkeleton.getBoneCount();b++){
                charBones[b] = charSkeleton.getBone(b);
                charBones[b].setUserControl(true);
                /** Note - The Joint selection ListBox could be populated using this array **/
                System.out.println("[ Bone: "+b+", Name: "+charBones[b].getName()+" ]");
            }
            System.out.println();
        }else{
            System.out.println( "Character skeleton is null");
        }        
        
        rootNode.attachChild(loadModel);
        
        /** Setup input mappings **/
        InputManager inputManager = stateManager.getApplication().getInputManager();
        inputManager.addMapping(CAM_X_N, X_MOUSE_L);
        inputManager.addMapping(CAM_X_P, X_MOUSE_R);
        inputManager.addMapping(CAM_Y_N, Y_MOUSE_D);
        inputManager.addMapping(CAM_Y_P, Y_MOUSE_U);
        inputManager.addMapping(SELECT, L_MOUSE);
        inputManager.addMapping(CAM_TOGGLE, M_MOUSE);
        inputManager.addMapping(CAM_MOVE_H, KEY_LCONTROL);
        inputManager.addMapping(CAM_MOVE_V, KEY_SHIFT_L);
        
        /** Setup input listeners **/
        inputManager.addListener(actionListener, SELECT, CAM_TOGGLE, CAM_MOVE_H, CAM_MOVE_V);
        inputManager.addListener(analogListener, CAM_X_N, CAM_X_P, CAM_Y_N, CAM_Y_P);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);        
        
        if(newBone == true){
            System.out.println("New Bone == true");
            currentBone = nextBone;
            getBoneInfo();
            
            
            float[] angles = null;
            /** Angles appear to be in Radians!!! **/
            angles  = rotData.toAngles(angles);

            //currentScreen.findNiftyControl("sliderX", Slider.class);
            
            /** The engine will use radians, the GUI will use degrees **/
            float degX = FastMath.RAD_TO_DEG * angles[0];
            float degY = FastMath.RAD_TO_DEG * angles[1];
            float degZ = FastMath.RAD_TO_DEG * angles[2];

            /** Round the values to 2 decimal places **/
            degX = (FastMath.floor(degX*100))/100;
            degY = (FastMath.floor(degY*100))/100;
            degZ = (FastMath.floor(degZ*100))/100;
            
            
            
            /** REMEMBER!!! Here, the slider value is set FROM the bone rotation!!!**/
            xslider.setValue(degX);
            yslider.setValue(degY);
            zslider.setValue(degZ);

            xlabel.setText("X: "+ degX);
            ylabel.setText("Y: "+ degY);
            zlabel.setText("Z: "+ degZ);    
            
            xlabel.layoutCallback();
            newBone = false;
        }
        
        if(rotBone == true){
            for(Bone bone : charBones){
                /** Grab bone info if the selectedItem matches a bone name **/
                if(bone.getName().equals(currentBone)){
                    rotData = bone.getLocalRotation();
                    ArrayList<Bone> children = bone.getChildren();
                    float[] angles = rotData.toAngles(null);
                    
                    /** The engine will use radians, the GUI will use degrees **/
                    float degX = FastMath.RAD_TO_DEG * angles[0];
                    float degY = FastMath.RAD_TO_DEG * angles[1];
                    float degZ = FastMath.RAD_TO_DEG * angles[2];
                    
                    /** Round the slider value to 2 decimal places **/
                    //sliderValue = (FastMath.floor(sliderValue*100))/100;
                    
                    switch(sliderAxis){
                        case "X":
                            degX = sliderValue;
                            break;
                        case "Y":
                            degY = sliderValue;
                            break;
                        case "Z":
                            degZ = sliderValue;
                            break;
                    }
                    
                    /** Update bone rotation**/
                    /** IMPORTANT!!! bone rotation is set FROM slider value, the engine needs RADIANS!!**/
                    
                    angles[0] = FastMath.DEG_TO_RAD * degX;
                    angles[1] = FastMath.DEG_TO_RAD * degY;
                    angles[2] = FastMath.DEG_TO_RAD * degZ;
                    
                    rotData.fromAngles(angles);
                    
                    bone.setLocalRotation(rotData);
                }
            }
            rotBone = false;
        }
        
        charSkeleton.updateWorldVectors();
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
    }

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
    
    /** Grabs rotation and position data from the current bone selected in listbox**/
    private void getBoneInfo(){
        for(Bone bone : charBones){
            /** Grab bone info if the selectedItem matches a bone name **/
            if(bone.getName().equals(currentBone)){

                System.out.println("Bone info...");
                Vector3f modelSpacePosition = bone.getModelSpacePosition();
                Quaternion modelSpaceRotation = bone.getModelSpaceRotation();
                Bone parent = bone.getParent();
                Vector3f localPosition = bone.getLocalPosition();
                Quaternion localRotation = bone.getLocalRotation();
                float[] toAngles = localRotation.toAngles(null);
                ArrayList<Bone> children = bone.getChildren();
                
                posData = localPosition;
                rotData = localRotation;
                
                float degX = FastMath.RAD_TO_DEG * toAngles[0];
                float degY = FastMath.RAD_TO_DEG * toAngles[1];
                float degZ = FastMath.RAD_TO_DEG * toAngles[2];
            
                System.out.println("    parent : "+ parent);
                System.out.println("    modelSpacePosition : "+ modelSpacePosition);
                System.out.println("    modelSpaceRotation : "+ modelSpaceRotation);
                System.out.println("    localPosition : "+ localPosition);
                System.out.println("    localRotation : rad"+ localRotation + ", deg(" + degX + ", " + degY + ", " + degZ + ")");
                System.out.println("    children : "+ children);
            }
        }
    }

    
}
