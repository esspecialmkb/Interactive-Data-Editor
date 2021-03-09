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
import com.jme3.system.AppSettings;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author kfdew
 */
public class BlenderAssetDev extends SimpleApplication{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BlenderAssetDev app = new BlenderAssetDev();
        AppSettings newSetting = new AppSettings(false);

        newSetting.setFrameRate(30);

        app.setSettings(newSetting);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        /** Get Blender data from j3o file **/
        Node blender_Data = (Node) assetManager.loadModel("Models/Blender/CharDev.j3o");
        System.out.println("First Node: " + blender_Data.getName());
        
        /** Get the Blender scenes **/
        List<Spatial> scene_Children = blender_Data.getChildren();
        System.out.println("Child Scenes: " + scene_Children.size());
        
        /** Print the names of the Blender scenes **/
        for(int s = 0; s < scene_Children.size(); s++){
            System.out.println("Name : " + scene_Children.get(s).getName());
        }
        
        boolean child = true;
        
        /** Get Blender data from j3o file **/
        Node animation_Scene = (Node) blender_Data.getChild(0);
        Node map_Scene = (Node) blender_Data.getChild(1);
        Node terrain_Scene = (Node) blender_Data.getChild(2);
        
        /** Get Animation Scene data **/
        System.out.println("Animation Scene child count: " + animation_Scene.getChildren().size());
        int s1Count = animation_Scene.getChildren().size();
        for(int i = 0;i<s1Count;i++){
            System.out.println("Child " + i + " name: " + animation_Scene.getChild(i).getName());
        }
        
        /** Get Map Scene data **/
        System.out.println("Map Scene child count: " + map_Scene.getChildren().size());
        int s2Count = map_Scene.getChildren().size();
        for(int i = 0;i<s2Count;i++){
            System.out.println("Child " + i + " name: " + map_Scene.getChild(i).getName());
        }
        
        /** Get Terrain Scene data **/
        System.out.println("Terrain Scene child count: " + terrain_Scene.getChildren().size());
        int s3Count = terrain_Scene.getChildren().size();
        for(int i = 0;i<s3Count;i++){
            System.out.println("Child " + i + " name: " + terrain_Scene.getChild(i).getName());
        }
        
        Node animCharacter = (Node) animation_Scene.getChild(0);
        rootNode.attachChild(animCharacter);
        
        /** Get the animation control **/
        AnimControl control = animCharacter.getChild(0).getControl(AnimControl.class);
        
        /** Print the names of animations**/
        Collection<String> animationNames = control.getAnimationNames();
        Object[] nameArray = animationNames.toArray();
        for(int a = 0; a < animationNames.size(); a++){
            System.out.println("Animation " + a + ": " + nameArray[a]);
        }
        
        /** Get Animation Channel **/
        AnimChannel channel = control.createChannel();
        Animation walk = control.getAnim("Walk");
        Animation run = control.getAnim("Run");
        Animation stand = control.getAnim("Action_Stand");
        
        channel.setAnim("Walk");
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
    }
    
    @Override
    public void simpleUpdate(float tpf){
        
    }
}
