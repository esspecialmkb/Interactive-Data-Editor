/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Blocks.Block;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class BlockDevspaceAppState extends AbstractAppState implements ScreenController{

    // This root node will be the parent of the animation scene
    private Node rootNode = new Node("Root Node");
    
    Block block;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        block = new Block();
        
        AssetManager assetManager = app.getAssetManager();
        
        // *************************************************************************
        // First mesh uses one solid color
        // *************************************************************************

        // Creating a geometry, and apply a single color material to it
        Geometry geom = new Geometry("OurMesh", block.getMesh());
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        // Attaching our geometry to the root node.
        rootNode.attachChild(geom);

        // *************************************************************************
        // Second mesh uses vertex colors to color each vertex
        // *************************************************************************
        Mesh cMesh = block.getMesh().clone();
        Geometry coloredMesh = new Geometry ("ColoredMesh", cMesh);
        Material matVC = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matVC.setBoolean("VertexColor", true);

        //We have 4 vertices and 4 color values for each of them.
        //If you have more vertices, you need 'new float[yourVertexCount * 4]' here!
        float[] colorArray = new float[4*4];
        int colorIndex = 0;

        //Set custom RGBA value for each Vertex. Values range from 0.0f to 1.0f
        for(int i = 0; i < 4; i++){
           // Red value (is increased by .2 on each next vertex here)
           colorArray[colorIndex++]= 0.1f+(.2f*i);
           // Green value (is reduced by .2 on each next vertex)
           colorArray[colorIndex++]= 0.9f-(0.2f*i);
           // Blue value (remains the same in our case)
           colorArray[colorIndex++]= 0.5f;
           // Alpha value (no transparency set here)
           colorArray[colorIndex++]= 1.0f;
        }
        // Set the color buffer
        cMesh.setBuffer(VertexBuffer.Type.Color, 4, colorArray);
        coloredMesh.setMaterial(matVC);
        // move mesh a bit so that it doesn't intersect with the first one
        coloredMesh.setLocalTranslation(4, 0, 0);
        rootNode.attachChild(coloredMesh);

//        /** Alternatively, you can show the mesh vertixes as points
//          * instead of coloring the faces. */
//        cMesh.setMode(Mesh.Mode.Points);
//        cMesh.setPointSize(10f);
//        cMesh.updateBound();
//        cMesh.setStatic();
//        Geometry points = new Geometry("Points", m);
//        points.setMaterial(mat);
//        rootNode.attachChild(points);

        // *************************************************************************
        // Third mesh will use a wireframe shader to show wireframe
        // *************************************************************************
        Mesh wfMesh = block.getMesh().clone();
        Geometry wfGeom = new Geometry("wireframeGeometry", wfMesh);
        Material matWireframe = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matWireframe.setColor("Color", ColorRGBA.Green);
        matWireframe.getAdditionalRenderState().setWireframe(true);
        wfGeom.setMaterial(matWireframe);
        wfGeom.setLocalTranslation(4, 4, 0);
        rootNode.attachChild(wfGeom);
    }
    
    @Override
    public void update(float tpf){
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        
    }

    @Override
    public void onStartScreen() {
        
    }

    @Override
    public void onEndScreen() {
        
    }
    
    public Node getRootNode(){
        return rootNode;
    }
    
}
