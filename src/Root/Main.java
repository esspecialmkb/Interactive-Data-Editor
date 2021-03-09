package Root;

import State.BlockDevspaceAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Curve;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    BlockDevspaceAppState blockspace;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        // disable the fly cam
        // Cam by default is at (0,0,10), looking at -z
        //cam.setLocation(new Vector3f(0,3,10));
        //cam.lookAt(new Vector3f(0,3,0), Vector3f.UNIT_Y);
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(30f);
        inputManager.setCursorVisible(true);
        
        blockspace = new BlockDevspaceAppState();
        viewPort.attachScene(blockspace.getRootNode());
        stateManager.attach(blockspace);
        
        //viewPort.setBackgroundColor(ColorRGBA.LightGray);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public class Block{
        
    }
    
    public class BlockFace{
        Mesh m;
        Geometry geom;
        
        Vector3f [] vertices = new Vector3f[4];
        Vector3f[] norms = new Vector3f[4];
        Vector2f [] texCoord = new Vector2f[4];
        short[] indexes = {2, 0, 1, 1, 3, 2};
        
        public void build(){
            
            // Vertex positions in space
            vertices[0] = new Vector3f(0,0,0);
            vertices[1] = new Vector3f(3,0,0);
            vertices[2] = new Vector3f(0,3,0);
            vertices[3] = new Vector3f(3,3,0);
            
            // Texture coordinates
            texCoord[0] = new Vector2f(0,0);
            texCoord[1] = new Vector2f(1,0);
            texCoord[2] = new Vector2f(0,1);
            texCoord[3] = new Vector2f(1,1);
            
            // Indexes. We define the order in which mesh should be constructed
            //short[] indexes = {2, 0, 1, 1, 3, 2};
            
            m = new Mesh();
            geom = new Geometry("OurMesh", m);
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Blue);
            geom.setMaterial(mat);
        }
        
        public Geometry getGeometry() { return geom; }
    }
}
