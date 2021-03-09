/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Root;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.Animation;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author esspe
 */
public class BlenderUtil1 {

    private Node blender_Data;
    private Node blender_Player;
    private List<Spatial> scene_Children;
    private List<Spatial> playerData_Children;
    private ArrayList<String> playerDataIndex;
    private int playerIndex = -1;
    private Node animation_Scene;
    private Node map_Scene;
    private Node terrain_Scene;
    private Node player_Scene;
    private Node[] tilePrefabs;
    protected AssetManager assetManager;
    protected Node animCharacter;
    protected AnimControl control;
    protected AnimChannel channel;
    protected Animation walk;
    protected Animation run;
    protected Animation stand;
    
    public BlenderUtil1(AssetManager assetManager){
        this.assetManager = assetManager;
        getBlenderData();
    }
    
    /** Grabs player data from charDev.j3o **/
    private void getPlayer(){
        /** We need to have the correct child index from the scene **/
        //Spatial spatialNode = animation_Scene.getChild(16);
        //spatialNode
        animCharacter = (Node) animation_Scene.getChild(16);
        //rootNode.attachChild(animCharacter);
        animCharacter.setLocalTranslation(0, 0, 0);
        
        
        /** Get the animation control **/
        control = animCharacter.getChild(0).getControl(AnimControl.class);
        
        /** Print the names of animations**/
        Collection<String> animationNames = control.getAnimationNames();
        Object[] nameArray = animationNames.toArray();
        for(int a = 0; a < animationNames.size(); a++){
            System.out.println("Animation " + a + ": " + nameArray[a]);
        }
        
        /** Get Animation Channel **/
        channel = control.createChannel();
        walk = control.getAnim("Walk");
        run = control.getAnim("Run");
        stand = control.getAnim("Action_Stand");
        
        channel.setAnim("Action_Stand");
    }
    
    /** Loads PlayerModel.j3o and returns player node
     * @return  Returns the player node, skips loading if player node already exists **/
    public Node getPlayerData(){
        if(blender_Player == null){
            /** Get player data **/
            blender_Player = (Node) assetManager.loadModel("Models/Blender/PlayerModel.j3o");
            String name = blender_Player.getName();

            /** Get player scene data **/
            Node blenderNode = (Node) blender_Player.getChildren().get(0);
            playerData_Children = blenderNode.getChildren();

            for(int p = 0; p < playerData_Children.size(); p++){
                String namePD = playerData_Children.get(p).getName();
                if(namePD.equals("AnimCharacter")){
                    playerIndex = p;
                }
                //System.out.println("Name : " + namePD);
            }
        }
        if(playerIndex == -1){
            return null;
        }
        return (Node) playerData_Children.get(playerIndex);
    }
    
    /** Depreciated **/
    public Node getPlayerNode(){
        return animCharacter;
    }
    
    private void getTilePrefabs(){
        /*
            0:  PreFab_Tile_Up
            1:  PreFab_Tile_East
            2:  PreFab_Tile_North
            3:  PreFab_Tile_West
            4:  PreFab_SlopeCover.001
            5:  PreFab_SlopeCover
            6:  PreFab_Stairs.001Cover
            7:  PreFab_StairsCover
            8:  PreFab_Slope.001
            9:  PreFab_Slope
            10: PreFab_Stairs.001
            11: PreFab_Stairs
            12: PreFab_Tile_South
            13: PreFab_Tile_Down
        
            14: Gun_Shotgun
            15: Gun_Pistol
            16: AnimCharacter
        */
        
        tilePrefabs = new Node[14];
        tilePrefabs[0] = (Node) animation_Scene.getChild(0);
        tilePrefabs[1] = (Node) animation_Scene.getChild(1);
        tilePrefabs[2] = (Node) animation_Scene.getChild(2);
        tilePrefabs[3] = (Node) animation_Scene.getChild(3);
        tilePrefabs[4] = (Node) animation_Scene.getChild(4);
        tilePrefabs[5] = (Node) animation_Scene.getChild(5);
        tilePrefabs[6] = (Node) animation_Scene.getChild(6);
        tilePrefabs[7] = (Node) animation_Scene.getChild(7);
        tilePrefabs[8] = (Node) animation_Scene.getChild(8);
        tilePrefabs[9] = (Node) animation_Scene.getChild(9);
        tilePrefabs[10] = (Node) animation_Scene.getChild(10);
        tilePrefabs[11] = (Node) animation_Scene.getChild(11);
        tilePrefabs[12] = (Node) animation_Scene.getChild(12);
        tilePrefabs[13] = (Node) animation_Scene.getChild(13);
        
        /* A colored lit cube. Needs light source! */ 
        Material floorMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); 
        floorMat.setBoolean("UseMaterialColors", true);  
        floorMat.setColor("Ambient", new ColorRGBA(0.113f,0.104f, 0.128f,1));
        floorMat.setColor("Diffuse", ColorRGBA.Green); 
        
        Material wallMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); 
        wallMat.setBoolean("UseMaterialColors", true); 
        wallMat.setColor("Ambient", new ColorRGBA(0.359f,0.367f, 0.312f,1));  
        wallMat.setColor("Diffuse", ColorRGBA.Green); 
        
        tilePrefabs[0].setMaterial(floorMat);
        tilePrefabs[1].setMaterial(wallMat);
        tilePrefabs[2].setMaterial(wallMat);
        tilePrefabs[3].setMaterial(wallMat);
        tilePrefabs[4].setMaterial(floorMat);
        tilePrefabs[5].setMaterial(floorMat);
        tilePrefabs[6].setMaterial(floorMat);
        tilePrefabs[7].setMaterial(floorMat);
        tilePrefabs[8].setMaterial(wallMat);
        tilePrefabs[9].setMaterial(floorMat);
        tilePrefabs[10].setMaterial(floorMat);
        tilePrefabs[11].setMaterial(floorMat);
        tilePrefabs[12].setMaterial(wallMat);
        tilePrefabs[13].setMaterial(wallMat);
    }
    
    private void getBlenderData(){
        /** Get Blender data from j3o file **/
        blender_Data = (Node) assetManager.loadModel("Models/Blender/CharDev.j3o");
        System.out.println("First Node: " + blender_Data.getName());
        
        /** Get the Blender scenes **/
        scene_Children = blender_Data.getChildren();
        System.out.println("Child Scenes: " + scene_Children.size());
        
        /** Print the names of the Blender scenes **/
        for(int s = 0; s < scene_Children.size(); s++){
            System.out.println("Name : " + scene_Children.get(s).getName());
        }
        
        boolean child = true;
        
        /** Get Blender data from j3o file **/
        animation_Scene = (Node) blender_Data.getChild(0);
        map_Scene = (Node) blender_Data.getChild(1);
        terrain_Scene = (Node) blender_Data.getChild(2);
        
        /** Get Animation Scene data **/
        System.out.println("Animation Scene child count: " + animation_Scene.getChildren().size());
        int s1Count = animation_Scene.getChildren().size();
        for(int i = 0;i<s1Count;i++){
            //System.out.println(i + ": " + animation_Scene.getChild(i).getName());
        }
        
        /** Get Player from Blender data **/
        getPlayer();
        
        /** Get Prefabs from Blender data **/
        getTilePrefabs();
        
        /** Get Map Scene data **/
        System.out.println("Map Scene child count: " + map_Scene.getChildren().size());
        int s2Count = map_Scene.getChildren().size();
        for(int i = 0;i<s2Count;i++){
            //System.out.println("Child " + i + " name: " + map_Scene.getChild(i).getName());
        }
        
        /** Get Terrain Scene data **/
        System.out.println("Terrain Scene child count: " + terrain_Scene.getChildren().size());
        int s3Count = terrain_Scene.getChildren().size();
        for(int i = 0;i<s3Count;i++){
            //System.out.println("Child " + i + " name: " + terrain_Scene.getChild(i).getName());
        }
    }
}
