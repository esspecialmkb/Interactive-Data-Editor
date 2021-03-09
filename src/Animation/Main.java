package Animation;

import Animation.GUIButton;
import com.jme3.animation.BoneTrack;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    protected String filePath = "C:\\Users\\kfdew\\Documents\\jME\\Projects\\Dev_Engine\\assets\\Data\\";
    
    protected GUIButton []buttons;
    protected BitmapFont fnt;
    protected Material guiMat;
    protected Material guiMatSel;
    protected Node guiCollider;
    
    protected int axisSel;
    protected int boneSel;
    protected int rotCmd;
    
    protected VirtualMesh mesh;
    protected VirtualSkeleton skeleton;
    //Current rotations
    protected float [][]rotations;
    
    //Nodes for bones
    private Node root;
    private Node pelvis;
    private Node hip_L;
    private Node hip_R;
    private Node knee_L;
    private Node knee_R;
    private Node torso;
    private Node chest;
    private Node shld_L;
    private Node shld_R;
    private Node elb_L;
    private Node elb_R;
    private Node neck;
    
    //Data store for keyframes
    protected int currentFrame;
    protected ArrayList<KeyFrame> keyFrames;
        
    public void loadVirtualMeshFile(){
        //BinaryImporter importer = BinaryImporter.getInstance();
        //assetManager.registerLocator(filePath, locatorClass);
        //assetManager.registerLocator(filePath, FileLocator.class);
        //importer.setAssetManager(assetManager);
        File fileMesh = new File(filePath + "DevVirtualMesh.txt");
        File fileSkeleton = new File(filePath + "DevVirtualSkeleton.txt");
        System.out.println("File open path: " + filePath);
               
        try{
            //Load in j3o format
            //mesh = (VirtualMesh) importer.load(file);
            
            int bufferSize = 8 * 1024;
            FileReader fileMeshReader = new FileReader(fileMesh);
            BufferedReader meshReader = new BufferedReader(fileMeshReader, bufferSize);
            mesh.readTxt(meshReader);
            meshReader.close();
            
            FileReader fileSkeletonReader = new FileReader(fileSkeleton);
            BufferedReader skeletonReader = new BufferedReader(fileSkeletonReader, bufferSize);
            skeleton.readTxt(skeletonReader);
            skeletonReader.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error: Failed to load VirtualMesh! IOException", ex);
        }
    }
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        
    }
    
    public void buildNodeHuman(){
        root = new Node("Root");
        pelvis = new Node("Pelvis");
        hip_L = new Node("Hip_L");
        hip_R = new Node("Hip_L");
        knee_L = new Node("Knee_L");
        knee_R = new Node("Knee_R");
        
        torso = new Node("Torso");
        chest = new Node("Chest");
        shld_L = new Node("Shld_L");
        shld_R = new Node("Shld_R");
        elb_L = new Node("Elb_L");
        elb_R = new Node("Elb_R");
        
        neck = new Node("Neck");
        
        Geometry uLeg_L = new Geometry("uLeg_L", new Box(2,4.5f,2));
        Geometry lLeg_L = new Geometry("lLeg_L", new Box(2,5.5f,2));
        
        Geometry uLeg_R = new Geometry("uLeg_R", new Box(2,4.5f,2));
        Geometry lLeg_R = new Geometry("lLeg_R", new Box(2,5.5f,2));
        
        Geometry hip = new Geometry("hip", new Box(4,1.5f,2));
        Geometry lBody = new Geometry("lBody", new Box(4,2.5f,2));
        Geometry uBody = new Geometry("uBody", new Box(4,3.5f,2));
        
        Geometry uArm_L = new Geometry("uArm_L", new Box(1.25f,3.5f,1.25f));
        Geometry lArm_L = new Geometry("uArm_L", new Box(1.25f,3.5f,1.25f));
        Geometry hand_L = new Geometry("hand_L", new Box(1.25f,1.25f,1.25f));
        
        Geometry uArm_R = new Geometry("uArm_R", new Box(1.25f,3.5f,1.25f));
        Geometry lArm_R = new Geometry("uArm_R", new Box(1.25f,3.5f,1.25f));
        Geometry hand_R = new Geometry("hand_R", new Box(1.25f,1.25f,1.25f));
        
        Geometry throat = new Geometry("Throat", new Box());
        Geometry head = new Geometry("Head", new Box());
        
        root.attachChild(pelvis);
        pelvis.setLocalTranslation(0, 20, 0);
        pelvis.attachChild(hip); //Add Geometry
        hip.setLocalTranslation(0,0.5f,0);
        
        pelvis.attachChild(hip_L);
        pelvis.attachChild(hip_R);
        hip_L.setLocalTranslation(-2,0,0);
        hip_R.setLocalTranslation(2,0,0);
        
        //Geometry
        hip_L.attachChild(uLeg_L);
        hip_R.attachChild(uLeg_R);
        uLeg_L.setLocalTranslation(0,-4.5f,0);
        uLeg_R.setLocalTranslation(0,-4.5f,0);
        
        hip_L.attachChild(knee_L);
        hip_R.attachChild(knee_R);
        knee_L.setLocalTranslation(0,-9,0);
        knee_R.setLocalTranslation(0,-9,0);
        
        //Geometry
        knee_L.attachChild(lLeg_L);
        knee_R.attachChild(lLeg_R);
        lLeg_L.setLocalTranslation(0,-5.5f,0);
        lLeg_R.setLocalTranslation(0,-5.5f,0);
        
        pelvis.attachChild(torso);
        torso.setLocalTranslation(0,2,0);
        torso.attachChild(chest);
        torso.attachChild(lBody);
        lBody.setLocalTranslation(0,2.5f,0);
        
        chest.attachChild(shld_L);
        chest.attachChild(shld_R);
        shld_L.setLocalTranslation(-5.25f,7,0);
        shld_R.setLocalTranslation(5.25f,7,0);
        
        shld_L.attachChild(uArm_L);
        shld_R.attachChild(uArm_R);
        uArm_L.setLocalTranslation(0,-3.5f,0);
        uArm_R.setLocalTranslation(0,-3.5f,0);
        
        chest.setLocalTranslation(0,5,0);
        chest.attachChild(uBody);
        uBody.setLocalTranslation(0,3.5f,0);
        
        shld_L.attachChild(elb_L);
        shld_R.attachChild(elb_R);
        elb_L.setLocalTranslation(0,-7,0);
        elb_R.setLocalTranslation(0,-7,0);
        elb_L.attachChild(lArm_L);
        elb_R.attachChild(lArm_R);
        lArm_L.setLocalTranslation(0,-3.5f,0);
        lArm_R.setLocalTranslation(0,-3.5f,0);
        
        root.setLocalScale(0.25f);
        
        Material blueMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	blueMat.setColor("Color", ColorRGBA.Blue);
        
        Material redMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        redMat.setColor("Color", ColorRGBA.Red);
        
        Material greenMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        greenMat.setColor("Color", ColorRGBA.Green);
        
        uLeg_L.setMaterial(redMat);
        uLeg_R.setMaterial(blueMat);
        lLeg_L.setMaterial(blueMat);
        lLeg_R.setMaterial(blueMat);
        
        hip.setMaterial(greenMat);
        uBody.setMaterial(blueMat);
        lBody.setMaterial(redMat);
        
        uArm_L.setMaterial(blueMat);
        uArm_R.setMaterial(blueMat);
        lArm_L.setMaterial(blueMat);
        lArm_R.setMaterial(blueMat);
        
        throat.setMaterial(blueMat);
        head.setMaterial(blueMat);
		
        rootNode.attachChild(root);
    }

    public void buildHuman(){
        //*/ Begin object creation
        mesh = new VirtualMesh();
        mesh.initMesh();
        skeleton = new VirtualSkeleton();
        skeleton.initSkeleton();
        
        // Load mesh and skeleton
        loadVirtualMeshFile();
        
        Mesh baseMesh = mesh.buildBlockMesh(true);
        Geometry geom = new Geometry("sceneMesh", baseMesh);
        
        //
        Material matSkin = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture skinTex = assetManager.loadTexture("Textures/SkinTemplate256_2.png");
        skinTex.setMagFilter(Texture.MagFilter.Nearest);
        skinTex.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        matSkin.setTexture("ColorMap", skinTex);
        
        skeleton.buildSkeleton(baseMesh);
        geom.getMesh();
        
        geom.setMaterial(matSkin);
        Node model = new Node("Mesh");
        model.attachChild(geom);
        rootNode.attachChild(model);
        
        SkeletonControl skeletonControl = new SkeletonControl(skeleton.internalSkeleton);
        geom.addControl(skeletonControl);
        //*/ End object creation
    }
    
    public void setupEnvironment(){
        // Here we set up the floor-grid
        Grid floorShape = new Grid(11, 11, 0.2f);
        Geometry gridGeo = new Geometry("Floor", floorShape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setLineWidth(1);
        mat.setColor("Color", ColorRGBA.White);
        gridGeo.setMaterial(mat);
        
        gridGeo.setLocalScale(10);
        rootNode.attachChild(gridGeo);
        gridGeo.center();
        // Set up the Animated Model
    }
    
    public void setupGUI(){
        axisSel = -1;
        boneSel = -1;
        rotCmd = -1;
        
        guiCollider = new Node("GUI Collider");
        
        guiNode.attachChild(guiCollider);
        fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        guiMat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        guiMat.setColor("Color", ColorRGBA.Gray);
        
        guiMatSel = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        guiMatSel.setColor("Color", ColorRGBA.Orange);
        
        //Define the button of the gui
        buttons = new GUIButton[18];
        
        buttons[0] = new GUIButton("Root", 0, 340, 100, 25, 1);
        buttons[1] = new GUIButton("Pelvis", 0, 370, 100, 25, 1);
        buttons[2] = new GUIButton("Hip L", 0, 400, 100, 25, 1);
        buttons[3] = new GUIButton("Hip R", 0, 430, 100, 25, 1);
        buttons[4] = new GUIButton("Knee L", 0, 460, 100, 25, 1);
        buttons[5] = new GUIButton("Knee R", 0, 490, 100, 25, 1);
        
        
        buttons[6] = new GUIButton("Torso", 0, 520, 100, 25, 1);
        buttons[7] = new GUIButton("Chest", 0, 550, 100, 25, 1);
        buttons[8] = new GUIButton("Shld L", 0, 580, 100, 25, 1);
        buttons[9] = new GUIButton("Shld R", 0, 610, 100, 25, 1);
        buttons[10] = new GUIButton("Elb L", 0, 640, 100, 25, 1);
        buttons[11] = new GUIButton("Elb R", 0, 670, 100, 25, 1);
        buttons[12] = new GUIButton("Neck", 0, 700, 100, 25, 1);
        
        buttons[13] = new GUIButton("X", 0, 100, 100, 25);
        buttons[14] = new GUIButton("Y", 105, 100, 100, 25);
        buttons[15] = new GUIButton("Z", 210, 100, 100, 25);
        buttons[16] = new GUIButton("+", 0, 70, 100, 25);
        buttons[17] = new GUIButton("-", 160, 70, 100, 25);
        System.out.println("Button y " + buttons[9].getType());
        
        for(int i = 0; i < 18; i++){
            buttons[i].buildElement(guiNode,guiCollider,fnt,guiMat);
            buttons[i].setID(i);
        }
    }
    
    public void doRotation(float value, int axis, int bone){
        Quaternion rotation = new Quaternion();
        rotations[bone][axis - 13] += (value *FastMath.DEG_TO_RAD);
        
        rotation.fromAngles(rotations[bone]);
        switch(bone){
            case 0:
                //skeleton.root.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                root.setLocalRotation(rotation);
                break;
            case 1:
                //skeleton.pelvis.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                pelvis.setLocalRotation(rotation);
                break;
            case 2:
                //skeleton.hip_L.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                hip_L.setLocalRotation(rotation);
                break;
            case 3:
                //skeleton.hip_R.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                hip_R.setLocalRotation(rotation);
                break;
            case 4:
                //skeleton.knee_L.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                knee_L.setLocalRotation(rotation);
                break;
            case 5:
                //skeleton.knee_R.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                knee_R.setLocalRotation(rotation);
                break;
            case 6:
                //skeleton.torso.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                torso.setLocalRotation(rotation);
                break;
            case 7:
                //skeleton.chest.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                chest.setLocalRotation(rotation);
                break;
            case 8:
                //skeleton.shld_L.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                shld_L.setLocalRotation(rotation);
                break;
            case 9:
                //skeleton.shld_R.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                shld_R.setLocalRotation(rotation);
                break;
            case 10:
                //skeleton.elb_L.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                elb_L.setLocalRotation(rotation);
                break;
            case 11:
                //skeleton.elb_R.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                elb_R.setLocalRotation(rotation);
                break;
            case 12:
                //skeleton.neck.setUserTransforms(Vector3f.ZERO, rotation, Vector3f.UNIT_XYZ);
                neck.setLocalRotation(rotation);
                break;
        }
    }    
    
    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(30f);
        flyCam.setDragToRotate(true);
        
        rotations = new float[13][3];
        
        //buildHuman();
        buildNodeHuman();
        
        setupEnvironment();
        
        setupGUI();
        
        setupInput();
        
        /*Geometry box = new Geometry("Box", new Box(1,1,1));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        box.setMaterial(mat);
        rootNode.attachChild(box);*/
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        if(rotCmd > 0){
            if(axisSel > 0 && boneSel > 0){
                if(rotCmd == 16){
                    doRotation(10,axisSel,boneSel);
                }else if(rotCmd == 17){
                    doRotation(-10,axisSel,boneSel);
                }
                //skeleton.internalSkeleton.updateWorldVectors();
            }
            rotCmd = -1;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private ActionListener actionListener = new ActionListener(){
        @Override
        public void onAction(String name, boolean pressed, float tpf){
            if(name.equals("Enter") && !pressed){
                //dumpVirtualMeshFile();
            }
            
            if(name.equals("Left Click") && !pressed){
                //Get position of mouse during mouse press
                Vector2f mousePos = inputManager.getCursorPosition();
                System.out.println("Button Clicked");
                for(GUIButton b : buttons){
                    //Check for intersections
                    if(b.checkBoundary(mousePos) == true){
                        
                        switch(b.getFlag()){
                            case 0:
                                //Commands
                                if(axisSel == -1){
                                    switch(b.getName()){
                                        case "X":   //13
                                            axisSel = b.getID();
                                            buttons[b.getID()].setMaterial(guiMatSel);
                                            break;
                                        case "Y":   //14
                                            axisSel = b.getID();
                                            buttons[b.getID()].setMaterial(guiMatSel);
                                            break;
                                        case "Z":   //15
                                            axisSel = b.getID();
                                            buttons[b.getID()].setMaterial(guiMatSel);
                                            break;
                                        case "+":   //16
                                            //Increase angle
                                            rotCmd = b.getID();
                                            break;
                                        case "-":   //17
                                            //Decrease angle
                                            rotCmd = b.getID();
                                            break;
                                    }
                                }else{
                                    switch(b.getName()){
                                        case "X":   //13
                                            buttons[axisSel].setMaterial(guiMat);
                                            axisSel = b.getID();
                                            buttons[b.getID()].setMaterial(guiMatSel);
                                            break;
                                        case "Y":   //14
                                            buttons[axisSel].setMaterial(guiMat);
                                            axisSel = b.getID();
                                            buttons[b.getID()].setMaterial(guiMatSel);
                                            break;
                                        case "Z":   //15
                                            buttons[axisSel].setMaterial(guiMat);
                                            axisSel = b.getID();
                                            buttons[b.getID()].setMaterial(guiMatSel);
                                            break;
                                        case "+":   //16
                                            //Increase angle
                                            rotCmd = b.getID();
                                            break;
                                        case "-":   //17
                                            //Decrease angle
                                            rotCmd = b.getID();
                                            break;
                                    }
                                }
                                
                                break;
                            case 1:
                                //Selection
                                //selectionJoint = b.getName();
                                if(boneSel == -1){
                                    boneSel = b.getID();
                                    buttons[b.getID()].setMaterial(guiMatSel);
                                }else{
                                    buttons[boneSel].setMaterial(guiMat);
                                    boneSel = b.getID();
                                    buttons[b.getID()].setMaterial(guiMatSel);
                                }
                                
                                break;
                        }
                    }
                }
            }
        }
    };
    
    
    public void setupInput(){
        //inputManager.addMapping()
        
        inputManager.addMapping("Shift", new KeyTrigger(KeyInput.KEY_LSHIFT),
                new KeyTrigger(KeyInput.KEY_RSHIFT));
        
        inputManager.addMapping("Tab", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Enter", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("Control", new KeyTrigger(KeyInput.KEY_LCONTROL),
                new KeyTrigger(KeyInput.KEY_RCONTROL));
        
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Right Click", new KeyTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Mouse Wheel Up",new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("Mouse Wheel Down",new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));

        // Test multiple listeners per mapping
        inputManager.addListener(actionListener, "Left Click", "Enter");
        //testInputManager.addListener(analogListener, "My Action");
    }
}
