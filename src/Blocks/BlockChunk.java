/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

/**
 *  The BlockChunk maintains a reference to a Block and uses it's mesh for a geometry
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class BlockChunk {
    /** Encapsulation Model **/
    Block block;
    
    /** Data Model **/
    Mesh mesh_Chunk;
    Vector3f[] verts_Chunk;
    Vector2f[] tex_Chunk;
    short[] tris_Chunk;
    
    Geometry geom;
    Material mat;
    
    public BlockChunk(AssetManager assetManager){
        // Creating a geometry, and apply a single color material to it
        Geometry geom = new Geometry("OurMesh",block.getMesh());
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
    }
    
    public Geometry getGeometry(){ return geom;}
}
