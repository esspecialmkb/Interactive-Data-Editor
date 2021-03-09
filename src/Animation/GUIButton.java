/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author Michael B.
 */
public class GUIButton {

    //private BitmapFont fnt;
    private Node guiRootNode;
    private Node guiCollider;
    // private Material guiMat;
    
    public GUIButton(){
        x= 0;
        y = 620;
        width = 50;
        height = 30;
    }

    public GUIButton(String name, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.flag = 0;
    }
    
    public GUIButton(String name, float x, float y, float width, float height, int flag){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.flag = flag;
    }

    public void buildElement(Node guiRootNode, Node guiCollider, BitmapFont fnt, Material guiMat){
        this.guiRootNode = guiRootNode;
        this.guiCollider = guiCollider;
        txt = new BitmapText(fnt,false);
        txt.setBox(new Rectangle(x, y, width, height));
        txt.setSize(fnt.getPreferredSize());
        txt.setText(name);
        txt.setLocalTranslation(0, txt.getHeight(), 0);

        guiRootNode.attachChild(txt);

        renderQuad = new Quad(width, height);
        geo = new Geometry("Button Quad", renderQuad);
        geo.setMaterial(guiMat);
        geo.setLocalTranslation(x,y,-1);
        guiCollider.attachChild(geo);
    }

    public boolean checkBoundary(Vector2f pos){
        if(pos.x > x && pos.x < (x + width)){
            if(pos.y > y && pos.y < (y + height)){
                return true;
            }
        }

        return false;
    }

    public void setMaterial(Material guiMat){
        geo.setMaterial(guiMat);
    }
    
    public String getName(){
        return this.name;
    }

    public String getType(){
        return "Button";
    }
    
    public void setFlag(int flag){
        this.flag = flag;
    }
    
    public int getFlag(){
        return this.flag;
    }

    public int getID(){
        return this.id;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    private int id;
    private String name;
    private int flag;
    private BitmapText txt;
    private float x,y,width,height;
    private Quad renderQuad;
    private Geometry geo;
    private Node parent;
}
