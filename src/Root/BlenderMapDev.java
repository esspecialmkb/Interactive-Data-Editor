/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Root;

import Animation.*;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.Animation;
import com.jme3.animation.BoneTrack;
import com.jme3.animation.Track;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Michael B.
 *  This project involves the import and usage of a 3d tile set and character 
 * created in Blender
 *  
 *  12/6/18
 *  Player faces south (Z+)
 */
public class BlenderMapDev extends SimpleApplication{
    
    private int tilePageSize = 16;
    private Node tileMapNode;
    //private int[][] tileMapPage_Data;
    private int[][][] tileMapPage_Data;
    //private Spatial[][] tileMapPage;
    private Node[][][] tileMapPage;
    private Node[] tilePrefabs;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BlenderMapDev app = new BlenderMapDev();
        AppSettings newSetting = new AppSettings(false);

        newSetting.setFrameRate(30);

        app.setSettings(newSetting);
        app.start();
    }
    
    private Node blender_Data;
    private List<Spatial> scene_Children;
    private Node animation_Scene;
    private Node map_Scene;
    private Node terrain_Scene;
    private Node animCharacter;
    private AnimControl control;
    private AnimChannel channel;
    private Animation walk;
    private Animation run;
    private Animation stand;
    
    private void getPlayer(){
        /** We need to have the correct child index from the scene **/
        animCharacter = (Node) animation_Scene.getChild(16);
        rootNode.attachChild(animCharacter);
        animCharacter.setLocalTranslation(0, 4, 0);
        
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
            System.out.println(i + ": " + animation_Scene.getChild(i).getName());
        }
        
        /** Get Player from Blender data **/
        getPlayer();
        
        /** Get Prefabs from Blender data **/
        getTilePrefabs();
        
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
    }
    
    /** Map utilities -- **/
    private void setMapTile(int x, int y, int z, int tile){
        //tileMapPage_Data[x][z] = tile;
        tileMapPage_Data[x][y][z] = tile;
    }
    
    /** Map utilities -- Look at the contents within**/
    private int getMapTile(int x, int y, int z){
        return tileMapPage_Data[x][y][z];
    }
    
    /** Map utilities -- Planning for voxel tile map **/
    private void setMapTileArea3(int px, int py, int pz, int sx, int sy, int sz, int tile){
        for(int lx = 0; lx < sx; lx++){
            for(int ly = 0; ly < sy; ly++){
                for(int lz = 0; lz < sz; lz++){
                    setMapTile(lx + px, ly + py, lz + pz, tile);
                }
            }
        }
    }
    
    /** Map utilities -- **/
    private void loadMap(int flag){
        /** Simulate loading map data **
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
        */
        switch(flag){
            case 1:
                System.out.println("Load Preset 1");
                /** First map preset **/
                setMapTileArea3(0,0,0,16,16,16,-1);
                
                setMapTileArea3(0,0,0,16,1,16,0);
                setMapTileArea3(6,1,6,4,6,4,0);
                break;
            case 2:
                /** SEcond map preset **/
                break;
                
            default:
                /** Default map **/
                //Floor
                /*
                for(int x = 0; x < tilePageSize; x++){
                    for(int z = 0; z < tilePageSize; z++){
                        //tileMapPage_Data[x][z] = 0;
                        tileMapPage_Data[x][y][z] = 0;
                    }
                }
                */
                //int y = 0;
                for(int x = 0; x < tilePageSize; x++){
                    for(int y = 0; y < tilePageSize; y++){
                        for(int z = 0; z < tilePageSize; z++){
                            if(y == 0){
                                tileMapPage_Data[x][y][z] = 0;
                            }else{
                                tileMapPage_Data[x][y][z] = -1;
                            }
                            
                            
                        }
                    }
                }
        }
    }
    
    /** Map utilities -- **/
    private void loadMap(){
        loadMap(0);
    }
    
    private void buildMap(){
        buildMap(0);
    }
    
    /** Map utilities -- Create the map here by reading tileMapPage_Data array **/
    private void buildMap(int flag){
        /** Build map **/
        
        //tileMapPage_Data = new int[tilePageSize][tilePageSize];
        tileMapPage_Data = new int[tilePageSize][tilePageSize][tilePageSize];
        
        loadMap(flag);
        
        /** Copy the prefabs to the grid according to tileMapPage_Data **/
        tileMapNode = new Node("TileMap Page 0.0");
        /*
        tileMapPage = new Node[tilePageSize][tilePageSize];
        for(int nx = 0; nx < tilePageSize; nx++){
            for(int nz = 0; nz < tilePageSize; nz++){
                /** Skip a tile if data is invalid **/ /*
                if(tileMapPage_Data[nx][nz] > -1){
                    tileMapPage[nx][nz] = tilePrefabs[tileMapPage_Data[nx][nz]].clone();
                    tileMapPage[nx][nz].setLocalTranslation(nx * 4, 0, nz * 4);
                    tileMapNode.attachChild(tileMapPage[nx][nz]);
                }//
            }
        }
        */
        
        /** 3D version **/
        tileMapPage = new Node[tilePageSize][tilePageSize][tilePageSize];
        for(int nx = 0; nx < tilePageSize; nx++){
            for(int ny = 0; ny < tilePageSize; ny++){
                for(int nz = 0; nz < tilePageSize; nz++){
                    /** Skip a tile if cell is empty **/
                    if(tileMapPage_Data[nx][ny][nz] > -1){
                        //Check in 6 directions for neighbors
                        
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
                        */
                        tileMapPage[nx][ny][nz] = new Node("Tile: " + nx + ", " + ny + ", " + nz);
                        String diag = "Neighbors: " + nx + ", " + ny + ", " + nz + ": ";
                        
                        /** Attach tile segments depending on neighbors **/
                        if(testNeighbor(nx,ny,nz,0) == false){
                            tileMapPage[nx][ny][nz].attachChild(tilePrefabs[2].clone());
                            diag += "North, ";
                        }
                        if(testNeighbor(nx,ny,nz,1) == false){
                            tileMapPage[nx][ny][nz].attachChild(tilePrefabs[12].clone());
                            diag += "South, ";
                        }
                        if(testNeighbor(nx,ny,nz,2) == false){
                            tileMapPage[nx][ny][nz].attachChild(tilePrefabs[1].clone());
                            diag += "East, ";
                        }
                        if(testNeighbor(nx,ny,nz,3) == false){
                            tileMapPage[nx][ny][nz].attachChild(tilePrefabs[3].clone());
                            diag += "West, ";
                        }
                        if(testNeighbor(nx,ny,nz,4) == false){
                            //The UP face represents the tile
                            tileMapPage[nx][ny][nz].attachChild(tilePrefabs[0].clone());
                            diag += "Up, ";
                        }
                        if(testNeighbor(nx,ny,nz,5) == false){
                            tileMapPage[nx][ny][nz].attachChild(tilePrefabs[13].clone());
                            diag += "Down, ";
                        }
                        
                        //System.out.println(diag);
                        /** Set local translation and attach to root node **/
                        tileMapPage[nx][ny][nz].setLocalTranslation(nx * 4, ny * 4, nz * 4);
                        tileMapNode.attachChild(tileMapPage[nx][ny][nz]);
                    }
                }
            }
        }
        
        /** Attach tileMapNode and Page to rootNode **/
        rootNode.attachChild(tileMapNode);
    }
    
    /** Map utilities -- Check for neighboring voxels within the range of the page chunk **/
    private boolean testNeighbor(int px, int py, int pz, int dir){
        boolean test = true;
        // Must be between 0 and tilePageSize
        // dir : N   S   E   W   U   D
        //       0   1   2   3   4   5
        //      -z  +z  +x  -x  +y  -y
        // If there is no neighbor in a certain dir, add the 3d tile
        switch(dir){
            case 0: // North
                if(tilePageRange(pz - 1)){
                    if(getMapTile(px, py, pz - 1) == -1){
                        test = false;
                    }
                }else{
                    //If we are at the border, assume no neighbor
                    //In the future, we check the neighboring page
                    test = false;
                }
                break;
            case 1:
                if(tilePageRange(pz + 1)){
                    if(getMapTile(px, py, pz + 1) == -1){
                        test = false;
                    }
                }else{
                    test = false;
                }
                break;
            case 2:
                if(tilePageRange(px + 1)){
                    if(getMapTile(px + 1, py, pz) == -1){
                        test = false;
                    }
                }else{
                    test = false;
                }
                break;
            case 3:
                if(tilePageRange(px - 1)){
                    if(getMapTile(px - 1, py, pz) == -1){
                        test = false;
                    }
                }else{
                    test = false;
                }
                break;
            case 4:
                if(tilePageRange(py + 1)){
                    if(getMapTile(px, py + 1, pz) == -1){
                        test = false;
                    }
                }else{
                    test = false;
                }
                break;
            case 5:
                if(tilePageRange(py - 1)){
                    if(getMapTile(px, py - 1, pz) == -1){
                        test = false;
                    }
                }else{
                    test = false;
                }
                break;
        }
        return test;
    }
    
    /** True when number is valid **/
    private boolean tilePageRange(int value){
        if( value < 0){
            //Negative value
            return false;
        }
        if( value >= tilePageSize){
            //Positive value
            return false;
        }
        return true;
    }

    @Override
    public void simpleInitApp() {
        /** Get Blender data **/
        getBlenderData();
        
        buildMap(1);
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
        
        /** Camera stuff... **/
        cam.lookAtDirection(new Vector3f(0.0f, 0.0f, 1.0f), Vector3f.UNIT_Y);
        cam.setLocation(new Vector3f(0.0f, 4.0f, -10.0f));
        flyCam.setMoveSpeed(60);
        
    }
    
    @Override
    public void simpleUpdate(float tpf){
        /** Update Loop **/
    }
}
