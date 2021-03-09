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
 *  The Block class is the 3d version
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class Block {
    Mesh m;
    Vector3f[] verts;
    Vector2f[] tex;
    short[] tris;
    
    /** Face visibility table**/
    boolean[] fVis; 
    
    private static float mVerts[] = {
        /** Rear - South face**/
        0,0,0,
        1,0,0,
        0,1,0,
        1,1,0,
        
        /** Front - North face**/
        1,0,1,
        0,0,1,
        1,1,1,
        0,1,1
    };
    
    private static float mTex[] = {
        /** Rear - South face**/
        0,0,
        1,0,
        0,1,
        1,1,
        
        /** Front - North face**/
        0,0,
        1,0,
        0,1,
        1,1
    };
    
    private static short mTris[] = {
        /** Rear - South face**/
        2,0,1,
        1,3,2,
        /** Front - North face**/
        6,4,5,
        5,7,6
            
    };
    
    public Block(){
        m = new Mesh();
        
        // Vertex positions in space
        verts = new Vector3f[8];
        
        /** Rear Face **/
        verts[0] = new Vector3f(mVerts[0],mVerts[1],mVerts[2]);
        verts[1] = new Vector3f(mVerts[3],mVerts[4],mVerts[5]);
        verts[2] = new Vector3f(mVerts[6],mVerts[7],mVerts[8]);
        verts[3] = new Vector3f(mVerts[9],mVerts[10],mVerts[11]);
        /** Front Face **/
        verts[4] = new Vector3f(mVerts[12],mVerts[13],mVerts[14]);
        verts[5] = new Vector3f(mVerts[15],mVerts[16],mVerts[17]);
        verts[6] = new Vector3f(mVerts[18],mVerts[19],mVerts[20]);
        verts[7] = new Vector3f(mVerts[21],mVerts[22],mVerts[23]);
        
        // Texture coordinates
        tex = new Vector2f[8];
        /** Rear Face **/
        tex[0] = new Vector2f(mTex[0],mTex[1]);
        tex[1] = new Vector2f(mTex[2],mTex[3]);
        tex[2] = new Vector2f(mTex[4],mTex[5]);
        tex[3] = new Vector2f(mTex[6],mTex[7]);
        /** Front Face **/
        tex[4] = new Vector2f(mTex[0],mTex[1]);
        tex[5] = new Vector2f(mTex[2],mTex[3]);
        tex[6] = new Vector2f(mTex[4],mTex[5]);
        tex[7] = new Vector2f(mTex[6],mTex[7]);
        
        // Indexes. We define the order in which mesh should be constructed
        tris = new short[12];
        /** Tracking offset for triangle indicies **/
        short trisOffset = (short)0;
        tris[0] = mTris[0];
        tris[1] = mTris[1];
        tris[2] = mTris[2];
        tris[3] = mTris[3];
        tris[4] = mTris[4];
        tris[5] = mTris[5];
        // tris == 6
        trisOffset = 6;
        tris[6] = (short) (mTris[0] + trisOffset);
        tris[7] = (short) (mTris[1] + trisOffset);
        tris[8] = (short) (mTris[2] + trisOffset);
        tris[9] = (short) (mTris[3] + trisOffset);
        tris[10] = (short) (mTris[4] + trisOffset);
        tris[11] = (short) (mTris[5] + trisOffset);
        
        // Setting buffers
        //m.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(verts));
        m.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(mVerts));
        m.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(mTex));
        m.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createShortBuffer(mTris));
        m.updateBound();
    }
    
    public Mesh getMesh(){ return m; }
}
