/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import com.jme3.animation.Bone;
import com.jme3.animation.Skeleton;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 *
 * @author Michael B.
 */
public class VirtualSkeleton {
    ArrayList<Byte> indices;
    ArrayList<Float> weights;
    ArrayList<Vector3f> bindPositions;
    ArrayList<Vector3f> bindScales;
    ArrayList<Quaternion> bindRotations;
    
    Skeleton internalSkeleton;
    
    Bone root;
    Bone pelvis;
    Bone hip_L;
    Bone hip_R;
    Bone knee_L;
    Bone knee_R;
    Bone torso;
    Bone chest;
    Bone shld_L;       //Shoulder_L
    Bone shld_R;        //Shoulder_R
    Bone elb_L;         //Elbow_L
    Bone elb_R;         //Elbow_R
    Bone neck;
        
    public void initSkeleton(){
        indices = new ArrayList<>();
        weights = new ArrayList<>();
        bindPositions = new ArrayList<>();
        bindScales = new ArrayList<>();
        bindRotations = new ArrayList<>();
        
        // Create skeleton
        root = new Bone("Root");
        pelvis = new Bone("Pelvis");
        hip_L = new Bone("Hip_L");
        hip_R = new Bone("Hip_R");
        knee_L = new Bone("Knee_L");
        knee_R = new Bone("Knee_R");
        //waist = new Bone("Waist");
        torso = new Bone("Torso");
        chest = new Bone("Chest");
        shld_L = new Bone("Shld_L");       //Shoulder_L
        shld_R = new Bone("Shld_R");        //Shoulder_R
        elb_L = new Bone("Elb_L");         //Elbow_L
        elb_R = new Bone("Elb_R");         //Elbow_R
        neck = new Bone("Neck");
    }
    
    public void addBoneIndex(int vert, int bone){
        // Assign vertex to bone index
        indices.add((byte)bone);
        indices.add((byte)bone);
        indices.add((byte)bone);
        indices.add((byte)bone);
        
        // Assign weights for influence
        weights.add(1f);
        weights.add(0f);
        weights.add(0f);
        weights.add(0f);
    }
    
    public void addBoneIndexMult(int vert, int boneA, int boneB){
        // Assign vertex to bone index
        indices.add((byte)boneA);
        indices.add((byte)boneA);
        indices.add((byte)boneB);
        indices.add((byte)boneB);
        
        // Assign weights for influence
        weights.add(0.5f);
        weights.add(0.5f);
        weights.add(0f);
        weights.add(0f);
    }
    
    public void addBindPosition(Vector3f position){
        bindPositions.add(position);
    }
    public void addBindScale(Vector3f scale){
        bindScales.add(scale);
    }
    public void addBindRotation(Quaternion rotation){
        bindRotations.add(rotation);
    }
    public Skeleton buildSkeleton(VirtualMesh vMesh){
        
        // Setup Bind Transformations for Skeleton
        //(Vector3f translation, Quaternion rotation = Quaternion.IDENTITY, Vector3f scale = Vector3f.UNIT_XYZ)
        root.setBindTransforms(bindPositions.get(0), bindRotations.get(0), bindScales.get(0));
        pelvis.setBindTransforms(bindPositions.get(1), bindRotations.get(1), bindScales.get(1));
        hip_L.setBindTransforms(bindPositions.get(2), bindRotations.get(2), bindScales.get(2));
        hip_R.setBindTransforms(bindPositions.get(3), bindRotations.get(3), bindScales.get(3));
        knee_L.setBindTransforms(bindPositions.get(4), bindRotations.get(4), bindScales.get(4));
        knee_R.setBindTransforms(bindPositions.get(5), bindRotations.get(5), bindScales.get(5));

        torso.setBindTransforms(bindPositions.get(6), bindRotations.get(6), bindScales.get(6));
        chest.setBindTransforms(bindPositions.get(7), bindRotations.get(7), bindScales.get(7));
        shld_L.setBindTransforms(bindPositions.get(8), bindRotations.get(8), bindScales.get(8));
        shld_R.setBindTransforms(bindPositions.get(9), bindRotations.get(9), bindScales.get(9));
        elb_L.setBindTransforms(bindPositions.get(10), bindRotations.get(10), bindScales.get(10));
        elb_R.setBindTransforms(bindPositions.get(11), bindRotations.get(11), bindScales.get(11));
        neck.setBindTransforms(bindPositions.get(12), bindRotations.get(12), bindScales.get(12));
        
        // Create the parent-child relationships of bones here
        root.addChild(pelvis);
        pelvis.addChild(hip_L);
        pelvis.addChild(hip_R);
        hip_L.addChild(knee_L);
        hip_R.addChild(knee_R);

        pelvis.addChild(torso);
        torso.addChild(chest);
        chest.addChild(shld_L);
        chest.addChild(shld_R);
        shld_L.addChild(elb_L);
        shld_R.addChild(elb_R);
        chest.addChild(neck);

        internalSkeleton = new Skeleton(new Bone[]{root, pelvis, hip_L, hip_R, knee_L, knee_R, torso, chest,
            shld_L, shld_R, elb_L, elb_R, neck});

        // Enable user controls
        root.setUserControl(true);
        pelvis.setUserControl(true);
        hip_L.setUserControl(true);
        hip_R.setUserControl(true);
        knee_L.setUserControl(true);
        knee_R.setUserControl(true);
        torso.setUserControl(true);
        chest.setUserControl(true);
        shld_L.setUserControl(true);
        shld_R.setUserControl(true);
        elb_L.setUserControl(true);
        elb_R.setUserControl(true);
        neck.setUserControl(true);
        
        /*
        // Future planning for BoneTrack animation data
        RootTarget = new BoneTrack(0);
        PelvisTarget = new BoneTrack(1);
        Hip_LTarget = new BoneTrack(2);
        Hip_RTarget = new BoneTrack(3);
        Knee_LTarget = new BoneTrack(4);
        Knee_RTarget = new BoneTrack(5);
        TorsoTarget = new BoneTrack(6);
        ChestTarget = new BoneTrack(7);
        Shld_LTarget = new BoneTrack(8);
        Shld_RTarget = new BoneTrack(9);
        Elb_LTarget = new BoneTrack(10);
        Elb_RTarget = new BoneTrack(11);
        NeckTarget = new BoneTrack(12);
        //*/
        
        Mesh sceneMesh = vMesh.buildBlockMesh();
        // Setup bone weight buffer
        
        
        VertexBuffer weightsHW = new VertexBuffer(VertexBuffer.Type.HWBoneWeight);
        VertexBuffer indicesHW = new VertexBuffer(VertexBuffer.Type.HWBoneIndex);
        indicesHW.setUsage(VertexBuffer.Usage.CpuOnly);
        weightsHW.setUsage(VertexBuffer.Usage.CpuOnly);
        sceneMesh.setBuffer(weightsHW);
        sceneMesh.setBuffer(indicesHW);

        // Setup bone weight buffer
        System.out.println("Bone Weights count/4: " + weights.size()/4);
        
        FloatBuffer floatBuffer = FloatBuffer.allocate( weights.size() );
        VertexBuffer weightsBuf = new VertexBuffer(VertexBuffer.Type.BoneWeight);
        weightsBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.Float, floatBuffer);
        

        // Setup bone index buffer
        System.out.println("Bone Indices count/4: " + indices.size()/4);
        System.out.println("Vertex vCount: " + vMesh.positions.size());
        System.out.println("Vertex mCount: " + sceneMesh.getVertexCount());
        
        ByteBuffer byteBuffer = ByteBuffer.allocate( indices.size() );       
        VertexBuffer indicesBuf = new VertexBuffer(VertexBuffer.Type.BoneIndex);
        indicesBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.UnsignedByte, byteBuffer);
        
        sceneMesh.setBuffer(weightsBuf);
        sceneMesh.setBuffer(indicesBuf);
        
        // Create bind pose buffers
        sceneMesh.generateBindPose(true);
        
        internalSkeleton.updateWorldVectors();
        return internalSkeleton;
    }
    
    public Skeleton buildSkeleton(Mesh mesh){
        
        // Setup Bind Transformations for Skeleton
        //(Vector3f translation, Quaternion rotation = Quaternion.IDENTITY, Vector3f scale = Vector3f.UNIT_XYZ)
        root.setBindTransforms(bindPositions.get(0), bindRotations.get(0), bindScales.get(0));
        pelvis.setBindTransforms(bindPositions.get(1), bindRotations.get(1), bindScales.get(1));
        hip_L.setBindTransforms(bindPositions.get(2), bindRotations.get(2), bindScales.get(2));
        hip_R.setBindTransforms(bindPositions.get(3), bindRotations.get(3), bindScales.get(3));
        knee_L.setBindTransforms(bindPositions.get(4), bindRotations.get(4), bindScales.get(4));
        knee_R.setBindTransforms(bindPositions.get(5), bindRotations.get(5), bindScales.get(5));

        torso.setBindTransforms(bindPositions.get(6), bindRotations.get(6), bindScales.get(6));
        chest.setBindTransforms(bindPositions.get(7), bindRotations.get(7), bindScales.get(7));
        shld_L.setBindTransforms(bindPositions.get(8), bindRotations.get(8), bindScales.get(8));
        shld_R.setBindTransforms(bindPositions.get(9), bindRotations.get(9), bindScales.get(9));
        elb_L.setBindTransforms(bindPositions.get(10), bindRotations.get(10), bindScales.get(10));
        elb_R.setBindTransforms(bindPositions.get(11), bindRotations.get(11), bindScales.get(11));
        neck.setBindTransforms(bindPositions.get(12), bindRotations.get(12), bindScales.get(12));
        
        // Create the parent-child relationships of bones here
        root.addChild(pelvis);
        pelvis.addChild(hip_L);
        pelvis.addChild(hip_R);
        hip_L.addChild(knee_L);
        hip_R.addChild(knee_R);

        pelvis.addChild(torso);
        torso.addChild(chest);
        chest.addChild(shld_L);
        chest.addChild(shld_R);
        shld_L.addChild(elb_L);
        shld_R.addChild(elb_R);
        chest.addChild(neck);

        internalSkeleton = new Skeleton(new Bone[]{root, pelvis, hip_L, hip_R, knee_L, knee_R, torso, chest,
            shld_L, shld_R, elb_L, elb_R, neck});

        // Enable user controls
        root.setUserControl(true);
        pelvis.setUserControl(true);
        hip_L.setUserControl(true);
        hip_R.setUserControl(true);
        knee_L.setUserControl(true);
        knee_R.setUserControl(true);
        torso.setUserControl(true);
        chest.setUserControl(true);
        shld_L.setUserControl(true);
        shld_R.setUserControl(true);
        elb_L.setUserControl(true);
        elb_R.setUserControl(true);
        neck.setUserControl(true);
        
        /*
        // Future planning for BoneTrack animation data
        RootTarget = new BoneTrack(0);
        PelvisTarget = new BoneTrack(1);
        Hip_LTarget = new BoneTrack(2);
        Hip_RTarget = new BoneTrack(3);
        Knee_LTarget = new BoneTrack(4);
        Knee_RTarget = new BoneTrack(5);
        TorsoTarget = new BoneTrack(6);
        ChestTarget = new BoneTrack(7);
        Shld_LTarget = new BoneTrack(8);
        Shld_RTarget = new BoneTrack(9);
        Elb_LTarget = new BoneTrack(10);
        Elb_RTarget = new BoneTrack(11);
        NeckTarget = new BoneTrack(12);
        //*/
        
        //Mesh sceneMesh = vMesh.buildBlockMesh();
        // Setup bone weight buffer
        VertexBuffer weightsHW = new VertexBuffer(VertexBuffer.Type.HWBoneWeight);
        VertexBuffer indicesHW = new VertexBuffer(VertexBuffer.Type.HWBoneIndex);
        //VertexBuffer weightsHW = new VertexBuffer(VertexBuffer.Type.BoneWeight);
        //VertexBuffer indicesHW = new VertexBuffer(VertexBuffer.Type.BoneIndex);
        indicesHW.setUsage(VertexBuffer.Usage.CpuOnly);
        weightsHW.setUsage(VertexBuffer.Usage.CpuOnly);
        mesh.setBuffer(weightsHW);
        mesh.setBuffer(indicesHW);

        // Setup bone weight and bone index buffers
        System.out.println("Bone Weights count/4: " + weights.size()/4);
        
        FloatBuffer floatBuffer = FloatBuffer.allocate( weights.size() );
        ByteBuffer byteBuffer = ByteBuffer.allocate( indices.size() ); 
        for(int i = 0; i < weights.size(); i++){
        
            // Assign vertex to bone index
            byteBuffer.array()[i] = indices.get(i);

            // Assign weights for influence
            floatBuffer.array()[i] = weights.get(i);
        }
        // Setup 
        /*System.out.println("Bone Indices count/4: " + indices.size()/4);
        System.out.println("Vertex vCount: " + vMesh.positions.size());
        System.out.println("Vertex mCount: " + sceneMesh.getVertexCount());*/
        
        
        VertexBuffer weightsBuf = new VertexBuffer(VertexBuffer.Type.BoneWeight);
        weightsBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.Float, floatBuffer);
        VertexBuffer indicesBuf = new VertexBuffer(VertexBuffer.Type.BoneIndex);
        indicesBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.UnsignedByte, byteBuffer);
        
        mesh.setBuffer(weightsBuf);
        mesh.setBuffer(indicesBuf);
        
        // Create bind pose buffers
        //mesh.generateBindPose(true);
        mesh.generateBindPose();
        mesh.setMaxNumWeights(2);
        internalSkeleton.updateWorldVectors();
        return internalSkeleton;
    }
    
    public void readTxt(BufferedReader reader) throws IOException{
        String delim = ":";
        String dataDelim = ",";
        String name = reader.readLine();
        //System.out.println(name);
        String dataRead = reader.readLine();
        String data[] = dataRead.split(delim);
        
        int indexCount = 0;
        if(data[0].equals("Bone Index Count")){
            indexCount = Integer.parseInt(data[1]);
            for(int i = 0; i < indexCount; i++){
                String indexRead = reader.readLine();
                String indexData[] = indexRead.split(delim);
                String byteData[] = indexData[1].split(dataDelim);
                
                Byte a = Byte.parseByte(byteData[0]);
                Byte b = Byte.parseByte(byteData[1]);
                Byte c = Byte.parseByte(byteData[2]);
                Byte d = Byte.parseByte(byteData[3]);
                
                indices.add(a);
                indices.add(b);
                indices.add(c);
                indices.add(d);
            }
        }
        
        dataRead = reader.readLine();
        data = dataRead.split(delim);
        int weightCount = 0;
        if(data[0].equals("Bone Weight Count")){
            weightCount = Integer.parseInt(data[1]);
            for(int i = 0; i < weightCount; i++){
                String weightRead = reader.readLine();
                String weightData[] = weightRead.split(delim);
                String floatData[] = weightData[1].split(dataDelim);
                
                float a = Float.parseFloat(floatData[0]);
                float b = Float.parseFloat(floatData[1]);
                float c = Float.parseFloat(floatData[2]);
                float d = Float.parseFloat(floatData[3]);
                
                weights.add(a);
                weights.add(b);
                weights.add(c);
                weights.add(d);
            }
        }
        
        dataRead = reader.readLine();
        data = dataRead.split(delim);
        int bindingCount = 0;
        if(data[0].equals("Binding Transform Count")){
            bindingCount = Integer.parseInt(data[1]);
            for(int i = 0; i < bindingCount; i++){
                String transformRead = reader.readLine();
                String transformData[] = transformRead.split(delim);
                String vectorData[] = transformData[1].split(dataDelim);
                
                float x = Float.parseFloat(vectorData[0]);
                float y = Float.parseFloat(vectorData[1]);
                float z = Float.parseFloat(vectorData[2]);
                
                bindPositions.add(new Vector3f(x,y,z));
            }
        }
        
        dataRead = reader.readLine();
        data = dataRead.split(delim);
        bindingCount = 0;
        if(data[0].equals("Binding Scale Count")){
            
            bindingCount = Integer.parseInt(data[1]);
            for(int i = 0; i < bindingCount; i++){
                String transformRead = reader.readLine();
                String transformData[] = transformRead.split(delim);
                String vectorData[] = transformData[1].split(dataDelim);
                
                float x = Float.parseFloat(vectorData[0]);
                float y = Float.parseFloat(vectorData[1]);
                float z = Float.parseFloat(vectorData[2]);
                
                bindScales.add(new Vector3f(x,y,z));
            }
        }
        
        dataRead = reader.readLine();
        data = dataRead.split(delim);
        bindingCount = 0;
        if(data[0].equals("Binding Rotation Count")){
            
            bindingCount = Integer.parseInt(data[1]);
            for(int i = 0; i < bindingCount; i++){
                String rotationRead = reader.readLine();
                String rotationData[] = rotationRead.split(delim);
                String quaternionData[] = rotationData[1].split(dataDelim);
                
                float w = Float.parseFloat(quaternionData[0]);
                float x = Float.parseFloat(quaternionData[1]);
                float y = Float.parseFloat(quaternionData[2]);
                float z = Float.parseFloat(quaternionData[3]);
                
                bindRotations.add(new Quaternion(x,y,z,w));
            }
        }
    }
    
    public void writeTxt(PrintWriter printWriter) throws IOException{
        printWriter.println("VirtualSkeleton");
        
        int indexCount = indices.size()/4;
        printWriter.println("Bone Index Count:" + indexCount);
        for(int i = 0; i < indexCount; i++){
            int offset = i * 4;
            printWriter.println(i + ":" + indices.get(offset) + "," + indices.get(offset + 1) + "," + indices.get(offset + 2) + "," + indices.get(offset + 3));
        }
        
        int weightCount = weights.size()/4;
        printWriter.println("Bone Weight Count:" + weightCount);
        for(int i = 0; i < weightCount; i++){
            int offset = i * 4;
            printWriter.println(i + ":" + weights.get(offset) + "," + weights.get(offset + 1) + "," + weights.get(offset + 2) + "," + weights.get(offset + 3));
        }
        
        int bindCount = bindPositions.size();
        printWriter.println("Binding Transform Count:" + bindCount);
        for(int i = 0; i < bindCount; i++){
            Vector3f position = bindPositions.get(i);
            printWriter.println(i + ":" + position.x + "," + position.y + "," + position.z);
            
        }
        
        bindCount = bindScales.size();
        printWriter.println("Binding Scale Count:" + bindCount);
        for(int i = 0; i < bindCount; i++){
            Vector3f scale = bindScales.get(i);
            printWriter.println(i + ":" + scale.x + "," + scale.y + "," + scale.z);
            
        }
        
        bindCount = bindRotations.size();
        printWriter.println("Binding Rotation Count:" + bindCount);
        for(int i = 0; i < bindCount; i++){
            Quaternion rotation = bindRotations.get(i);
            printWriter.println(i + ":" + rotation.getW() + "," + rotation.getX() + "," + rotation.getY() + "," + rotation.getZ());
            
        }
        
    }
}
