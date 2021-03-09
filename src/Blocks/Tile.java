/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blocks;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 *  Tile holds mesh data for a 2d tile mesh
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class Tile {
    Mesh m;
    Vector3f[] verts;
    Vector2f[] tex;
    short[] tris;
    
    private static float mVerts[] = {
        0,0,0,
        1,0,0,
        0,1,0,
        1,1,0
    };
    
    private static float mTex[] = {
        0,0,
        1,0,
        0,1,
        1,1
    };
    
    private static short mTris[] = {
        2,0,1,
        1,3,2
    };
    
    public Tile(){
        m = new Mesh();
        
        // Vertex positions in space
        verts = new Vector3f[4];
        verts[0] = new Vector3f(mVerts[0],mVerts[1],mVerts[2]);
        verts[1] = new Vector3f(mVerts[3],mVerts[4],mVerts[5]);
        verts[2] = new Vector3f(mVerts[6],mVerts[7],mVerts[8]);
        verts[3] = new Vector3f(mVerts[9],mVerts[10],mVerts[11]);
        
        // Texture coordinates
        tex = new Vector2f[4];
        tex[0] = new Vector2f(mTex[0],mTex[1]);
        tex[1] = new Vector2f(mTex[2],mTex[3]);
        tex[2] = new Vector2f(mTex[4],mTex[5]);
        tex[3] = new Vector2f(mTex[6],mTex[7]);
        
        // Indexes. We define the order in which mesh should be constructed
        tris = new short[6];
        tris[0] = mTris[0];
        tris[1] = mTris[1];
        tris[2] = mTris[2];
        tris[3] = mTris[3];
        tris[4] = mTris[4];
        tris[5] = mTris[5];
        
        // Setting buffers
        m.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(verts));
        m.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(tex));
        m.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createShortBuffer(tris));
        m.updateBound();
    }
    
    public Mesh getMesh(){ return m; }
}
