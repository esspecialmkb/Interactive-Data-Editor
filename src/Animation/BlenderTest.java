/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.Animation;
import com.jme3.animation.BoneTrack;
import com.jme3.animation.Track;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Collection;

/**
 *  We need a way to parse the contents of a blender file.
 * 
 *  There are 2 Goals:
 * Implement functionality to list objects within the blend file
 * Implement object display of items within the blend file
 * 
 * These two goals are separated to ensure decoupling between file parsing and model display
 * @author Michael B.
 */
public class BlenderTest extends SimpleApplication{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BlenderTest app = new BlenderTest();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //assetManager.registerLoader(BlenderModelLoader.class, "blend");
        
        Node model = (Node) assetManager.loadModel("Models/Blender/testAnimMesh/testAnimMesh.j3o");
        
        //rootNode.attachChild(model);
        System.out.println("Model child count: " + model.getChildren().size());
        Node child1 = (Node) model.getChild(0);
        System.out.println("Child1 child count: " + child1.getChildren().size());
        int c1Count = child1.getChildren().size();
        for(int i = 0;i<c1Count;i++){
            System.out.println("Child " + i + " name: " + child1.getChild(i).getName());
        }
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
        
        Node player = (Node) child1.getChild(0);    //Spatial
        System.out.println("Player children: " + player.getChildren().size());
        rootNode.attachChild(player);
        
        AnimControl control = player.getChild(0).getControl(AnimControl.class);
        Collection<String> animationNames = control.getAnimationNames();
        System.out.println("Anim count: " + animationNames.size());
        AnimChannel channel = control.createChannel();
        System.out.println("------------------------");
        Animation action_Walk = control.getAnim("Action Walk");
        System.out.println("Anim length: " + action_Walk.getLength());
        
        Track[] tracks;
        tracks = action_Walk.getTracks();
        System.out.println("Num tracks: " + tracks.length);
        System.out.println("Track length: " + tracks[0].getLength());
        //channel.setAnim("Action Walk");
        
        BoneTrack track = (BoneTrack) tracks[6];
        
        Quaternion[] rotations = track.getRotations();
        System.out.println("Num rotations: " + rotations.length);
        for(int i = 1; i < rotations.length; i++){
            float[] toAngles = new float[3];
            rotations[i].toAngles(toAngles);
            System.out.println("Rotation " + i +" :[ " + toAngles[0] * FastMath.RAD_TO_DEG + ", " + toAngles[1] * FastMath.RAD_TO_DEG + ", " + toAngles[2] * FastMath.RAD_TO_DEG + " ]");
        }
        
    }
    
    @Override
    public void simpleUpdate(float tpf){
        
    }
}
