/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

/**
 *
 * @author Michael A.
 */
public class KeyFrame {
    public float[] root;
    public float[] pelvis;
    public float[] hip_L;
    public float[] hip_R;
    public float[] knee_L;
    public float[] knee_R;
    public float[] torso;
    public float[] chest;
    public float[] shld_L;
    public float[] shld_R;
    public float[] elb_L;
    public float[] elb_R;
    public float[] neck;
    
    public int frame;
    public int type;
    public float preTime;
    public String name;
    
    public KeyFrame(){
        this.frame = -1;
        this.type = -1;
        this.name = "KeyFrame";
        
        root = new float[3];
        pelvis = new float[3];
        hip_L = new float[3];
        hip_R = new float[3];
        knee_L = new float[3];
        knee_R = new float[3];
        torso = new float[3];
        chest = new float[3];
        shld_L = new float[3];
        shld_R = new float[3];
        elb_L = new float[3];
        elb_R = new float[3];
        neck = new float[3];
    }
}
