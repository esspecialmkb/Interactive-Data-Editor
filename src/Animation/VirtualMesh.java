/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael B.
 */
public class VirtualMesh implements Savable{
    ArrayList<Vector3f> positions;
    ArrayList<Float> posU;
    ArrayList<Vector2f> tCoords;
    ArrayList<Short> indexList;
    ArrayList<Float> normals;
    
    Mesh internalMesh;
    short offset;
    public boolean hasTCoords;
    public boolean init;
    
    //HumanoidControl originally used grid based texture mapping
    //Curtesy float representing 32x32 unit grid
    //protected float texture32Unit = 0.03125f;
    
    public void VirtualMesh(){
        //Empty constructor
    }
    
    public void initMesh(){
        initMesh(false);
    }
    
    public void initMesh(boolean useTCoords){
        if(init == true){
            resetMesh();
        }else{
            init = true;
            positions = new ArrayList();
            posU = new ArrayList();
            indexList = new ArrayList();
            normals = new ArrayList();
            tCoords = new ArrayList();
            offset = 0;
            hasTCoords = useTCoords;
        }
        init = true;
    }
    //CLEAR ARRAYLISTS
    public void resetMesh(){
        
        if(internalMesh != null){
            internalMesh.clearBuffer(VertexBuffer.Type.Position);
            internalMesh.clearBuffer(VertexBuffer.Type.Index);
            internalMesh.clearBuffer(VertexBuffer.Type.Normal);
            internalMesh.clearBuffer(VertexBuffer.Type.TexCoord);
        }
        positions.clear();
        tCoords.clear();
        indexList.clear();
        normals.clear();
        offset = 0;
    }

    public void updateIndex(){
        offset = (short)positions.size();
    }

    public void addVertex(Vector3f pos){
        positions.add(pos);
        
        //singleVertex(pos.x);
        //singleVertex(pos.y);
        //singleVertex(pos.z);
    }
    
    public void addTriangle(short a, short b, short c){
        indexList.add((short)(a));
        indexList.add((short)(b));
        indexList.add((short)(c));
        //offset = (short) positions.size();
    }
    public void addNormal(float nx, float ny, float nz){
        normals.add(nx);
        normals.add(ny);
        normals.add(nz);
    }
    
    public void addTCoords(Vector2f tCoord){
        tCoords.add(tCoord);
    }

    public Mesh buildBlockMesh() {
        if(internalMesh != null){
            return internalMesh;
        }
        
        Mesh mesh = new Mesh();
        
        //Float[] pVertsU = new Float[posU.size()/3];
        //Vector3f[] pVertices = new Vector3f[posU.size()/3];
        Vector3f[] pVertices = new Vector3f[positions.size()];
        System.out.println("Position count: " + positions.size());
        Iterator<Float> posUIter = posU.iterator();
        Iterator<Vector3f> positionsIter = positions.iterator();
        for(int i=0;positionsIter.hasNext();i++){
            pVertices[i] = positionsIter.next();
        }
        
        short[] indices = new short[indexList.size()];
        Iterator<Short> indicesIterator = indexList.iterator();
        for(int i=0;indicesIterator.hasNext();i++){
            indices[i] = indicesIterator.next();
        }

        //float[] fNormals = new float[normals.size()]; 
        Vector3f[] pNormals = new Vector3f[normals.size()/3];
        System.out.println("Normals count: " + normals.size()/3);
        Iterator<Float> normalsIterator = normals.iterator();
        for(int i=0;normalsIterator.hasNext();i++){
            pNormals[i] = new Vector3f(normalsIterator.next(),normalsIterator.next(),normalsIterator.next());
        }
        
        //mesh.setDynamic();

        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(pVertices));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createShortBuffer(indices));
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(pNormals));
        
        if(hasTCoords){
            Vector2f[] pTCoors = new Vector2f[tCoords.size()];
            Iterator<Vector2f> tCoordIterator = tCoords.iterator();
            for(int i=0;tCoordIterator.hasNext();i++){
                pTCoors[i] = tCoordIterator.next();
            }
            mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(pTCoors));
        }
        
        
        mesh.updateBound();
        mesh.updateCounts();

        //System.out.println(mesh.getTriangleCount() + " Tris, " + mesh.getVertexCount() + " Verts");
        return mesh;
    }
    
    public Mesh buildBlockMesh(boolean keepInternal) {
        Mesh mesh = new Mesh();
        
        
        
        if(keepInternal == true){
           internalMesh = new Mesh();
           
           //Float[] pVertsU = new Float[posU.size()/3];
            //Vector3f[] pVertices = new Vector3f[posU.size()/3];
            Vector3f[] pVertices = new Vector3f[positions.size()];
            System.out.println("Position count: " + positions.size());
            Iterator<Float> posUIter = posU.iterator();
            Iterator<Vector3f> positionsIter = positions.iterator();
            for(int i=0;positionsIter.hasNext();i++){
                pVertices[i] = positionsIter.next();
            }

            short[] indices = new short[indexList.size()];
            Iterator<Short> indicesIterator = indexList.iterator();
            for(int i=0;indicesIterator.hasNext();i++){
                indices[i] = indicesIterator.next();
            }

            //float[] fNormals = new float[normals.size()]; 
            Vector3f[] pNormals = new Vector3f[normals.size()/3];
            System.out.println("Normals count: " + normals.size()/3);
            Iterator<Float> normalsIterator = normals.iterator();
            for(int i=0;normalsIterator.hasNext();i++){
                pNormals[i] = new Vector3f(normalsIterator.next(),normalsIterator.next(),normalsIterator.next());
            }

            //mesh.setDynamic();

            internalMesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(pVertices));
            internalMesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createShortBuffer(indices));
            internalMesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(pNormals));

            if(hasTCoords){
                Vector2f[] pTCoors = new Vector2f[tCoords.size()];
                Iterator<Vector2f> tCoordIterator = tCoords.iterator();
                for(int i=0;tCoordIterator.hasNext();i++){
                    pTCoors[i] = tCoordIterator.next();
                }
                internalMesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(pTCoors));
            }


            internalMesh.updateBound();
            internalMesh.updateCounts();
            
            mesh = internalMesh;
        }else{
            //Float[] pVertsU = new Float[posU.size()/3];
            //Vector3f[] pVertices = new Vector3f[posU.size()/3];
            Vector3f[] pVertices = new Vector3f[positions.size()];
            System.out.println("Position count: " + positions.size());
            Iterator<Float> posUIter = posU.iterator();
            Iterator<Vector3f> positionsIter = positions.iterator();
            for(int i=0;positionsIter.hasNext();i++){
                pVertices[i] = positionsIter.next();
            }

            short[] indices = new short[indexList.size()];
            Iterator<Short> indicesIterator = indexList.iterator();
            for(int i=0;indicesIterator.hasNext();i++){
                indices[i] = indicesIterator.next();
            }

            //float[] fNormals = new float[normals.size()]; 
            Vector3f[] pNormals = new Vector3f[normals.size()/3];
            System.out.println("Normals count: " + normals.size()/3);
            Iterator<Float> normalsIterator = normals.iterator();
            for(int i=0;normalsIterator.hasNext();i++){
                pNormals[i] = new Vector3f(normalsIterator.next(),normalsIterator.next(),normalsIterator.next());
            }

            //mesh.setDynamic();

            mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(pVertices));
            mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createShortBuffer(indices));
            mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(pNormals));

            if(hasTCoords){
                Vector2f[] pTCoors = new Vector2f[tCoords.size()];
                Iterator<Vector2f> tCoordIterator = tCoords.iterator();
                for(int i=0;tCoordIterator.hasNext();i++){
                    pTCoors[i] = tCoordIterator.next();
                }
                mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(pTCoors));
            }


            mesh.updateBound();
            mesh.updateCounts();
        }
        
        //System.out.println(mesh.getTriangleCount() + " Tris, " + mesh.getVertexCount() + " Verts");
        return mesh;
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        //Write the virtual mesh to a file
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write("VirtualMesh", "ObjectType", "VirtualMesh");
        
        Vector3f[] pVertices = new Vector3f[positions.size()];
        System.out.println("Position count: " + positions.size());
        //Write number of vertices
        capsule.write(positions.size(), "Vertex Count", 0);
        
        Iterator<Float> posUIter = posU.iterator();
        Iterator<Vector3f> positionsIter = positions.iterator();
        for(int i=0;positionsIter.hasNext();i++){
            
            pVertices[i] = positionsIter.next();
            //Write each vertex to file
            capsule.write(pVertices[i].x, "Vertex " + i + "X Coord", 0f);
            capsule.write(pVertices[i].y, "Vertex " + i + "Y Coord", 0f);
            capsule.write(pVertices[i].z, "Vertex " + i + "Z Coord", 0f);
        }
        
        short[] indices = new short[indexList.size()];
        //Write number of triangle indices
        capsule.write(indexList.size(),"Index count", 0);
        
        Iterator<Short> indicesIterator = indexList.iterator();
        //3 indices to a triangle
        int t = 0;
        int counter = 0;
        for(int i=0;indicesIterator.hasNext();i++){
            
            indices[i] = indicesIterator.next();
            //Write each triangle index to file
            capsule.write(indices[i], "Triangle " + t + ": index " + i, 0);
            counter++;
            if(counter == 2){
                t++;
                counter = 0;
            }
        }

        //float[] fNormals = new float[normals.size()]; 
        Vector3f[] pNormals = new Vector3f[normals.size()/3];
        System.out.println("Normals count: " + normals.size()/3);
        //Write number of normals
        capsule.write(normals.size()/3, "Normals Count", 0);
        
        Iterator<Float> normalsIterator = normals.iterator();
        for(int i=0;normalsIterator.hasNext();i++){
            
            pNormals[i] = new Vector3f(normalsIterator.next(),normalsIterator.next(),normalsIterator.next());
            
            //Write normals to file
            capsule.write(pNormals[i].x, "Normal " + i + "X Coord", 0f);
            capsule.write(pNormals[i].y, "Normal " + i + "Y Coord", 0f);
            capsule.write(pNormals[i].z, "Normal " + i + "Z Coord", 0f);
        }
        
        if(hasTCoords){
            Vector2f[] pTCoors = new Vector2f[tCoords.size()];
            capsule.write(tCoords.size(), "TCoords count", 0);
            Iterator<Vector2f> tCoordIterator = tCoords.iterator();
            for(int i=0;tCoordIterator.hasNext();i++){
                pTCoors[i] = tCoordIterator.next(); 
                
                //Write texture coordinates to file
                capsule.write(pTCoors[i].x, "TCoord " + i + "X Coord", 0f);
                capsule.write(pTCoors[i].y, "TCoord " + i + "Y Coord", 0f);
            }
        }else{
            capsule.write(0, "TCoords count", 0);
        }
    }
    
    public void readTxt(BufferedReader reader) throws IOException{
        initMesh();
        String delim = ":";
        String dataDelim = ",";
        String name = reader.readLine();
        //System.out.println(name);
        String dataRead = reader.readLine();
        String data[] = dataRead.split(delim);
        //System.out.println("first read " + data[0] + ":" + data[1]);
        int vertexCount = 0;
        if(data[0].equals("Vertex Count")){
            
            vertexCount = Integer.parseInt(data[1].trim());
            for(int i = 0;i < vertexCount; i++){
                String vertexRead = reader.readLine();
                String vertexData[] = vertexRead.split(delim);
                String vectorData[] = vertexData[1].split(dataDelim);
                
                float x = Float.parseFloat(vectorData[0]);
                float y = Float.parseFloat(vectorData[1]);
                float z = Float.parseFloat(vectorData[2]);
                
                addVertex(new Vector3f(x,y,z));
            }
        }
        
        //dataRead = "";
        dataRead = reader.readLine();
        data = dataRead.split(delim);
        //System.out.println("second read " + data[0] + ":" + data[1]);
        int indicesCount = 0;
        if(data[0].equals("Triangle Count")){
            indicesCount = Integer.parseInt(data[1].trim());
            for(int i = 0; i < indicesCount; i++){
                String indexRead = reader.readLine();
                String indexData[] = indexRead.split(delim);
                String triangleData[] = indexData[1].split(dataDelim);
                
                short t0 = Short.parseShort(triangleData[0].trim());
                short t1 = Short.parseShort(triangleData[1].trim());
                short t2 = Short.parseShort(triangleData[2].trim());
                
                addTriangle(t0,t1,t2);
            }
        }
        
        dataRead = reader.readLine();
        data = dataRead.split(delim);
        //System.out.println("third read " + data[0] + ":" + data[1]);
        int normalsCount = 0;
        if(data[0].equals("Normals count")){
            normalsCount = Integer.parseInt(data[1].trim());
            for(int i = 0; i < normalsCount; i++){
                String normalRead = reader.readLine();
                String normalData[] = normalRead.split(delim);
                String vectorData[] = normalData[1].split(dataDelim);
                
                float x = Float.parseFloat(vectorData[0].trim());
                float y = Float.parseFloat(vectorData[1].trim());
                float z = Float.parseFloat(vectorData[2].trim());
                
                addNormal(x,y,z);
            }
        }
        
        dataRead = reader.readLine();
        data = dataRead.split(delim);
        System.out.println("fourth read " + data[0] + ":" + data[1]);
        int tCoordsCount = 0;
        if(data[0].equals("TCoords count")){
            tCoordsCount = Integer.parseInt(data[1].trim());
            if(tCoordsCount > 0){
                hasTCoords = true;
                for(int i = 0; i < tCoordsCount; i++){
                    String tCoordRead = reader.readLine();
                    String tCoordData[] = tCoordRead.split(delim);
                    String vectorData[] = tCoordData[1].split(dataDelim);

                    float x = Float.parseFloat(vectorData[0].trim());
                    float y = Float.parseFloat(vectorData[1].trim());

                    addTCoords(new Vector2f(x,y));
                }
            }
        }
    }
    
    public void writeTxt(PrintWriter printWriter) throws IOException{
        printWriter.write("VirtualMesh");
        printWriter.println();
        
        Vector3f[] pVertices = new Vector3f[positions.size()];
        
        printWriter.write("Vertex Count:" + positions.size());
        printWriter.println();
        Iterator<Vector3f> positionsIter = positions.iterator();
        for(int i=0;positionsIter.hasNext();i++){
            
            pVertices[i] = positionsIter.next();
            printWriter.print(i + ":" + pVertices[i].x + "," + pVertices[i].y + "," + pVertices[i].z );
            printWriter.println();
        }
        
        //Write number of triangle indices
        printWriter.write("Triangle Count:" + indexList.size()/3);
        printWriter.println();
        
        Iterator<Short> indicesIterator = indexList.iterator();
        //3 indices to a triangle
        int t = 0;
        int counter = 0;
        for(int i=0;indicesIterator.hasNext();i++){
            
            //indices[i] = indicesIterator.next();
            //Write each triangle index to file
            printWriter.write(i + ":" + indicesIterator.next() + "," + indicesIterator.next() + "," + indicesIterator.next());
            printWriter.println();
        }
        
        Vector3f[] pNormals = new Vector3f[normals.size()/3];
        //Write number of normals
        printWriter.write("Normals count:" + normals.size()/3);
        printWriter.println();
        
        Iterator<Float> normalsIterator = normals.iterator();
        for(int i=0;normalsIterator.hasNext();i++){           
            //Write normals to file            
            printWriter.write(i + ":" + normalsIterator.next() + "," + normalsIterator.next() + "," + normalsIterator.next());
            printWriter.println();
        }
        
        if(hasTCoords){
            Vector2f[] pTCoors = new Vector2f[tCoords.size()];
            printWriter.write("TCoords count:" + tCoords.size());
            printWriter.println();
            Iterator<Vector2f> tCoordIterator = tCoords.iterator();
            for(int i=0;tCoordIterator.hasNext();i++){
                pTCoors[i] = tCoordIterator.next(); 
                
                //Write texture coordinates to file
                printWriter.write(i + ":" + pTCoors[i].x + "," + pTCoors[i].y );
                printWriter.println();
            }
        }else{
            printWriter.write("TCoords count:" + 0);
            printWriter.println();
        }
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        //Read the virtual mesh from a file
        InputCapsule capsule = im.getCapsule(this);
        
        System.out.println("Read Name");
        String name = capsule.readString("ObjectType", "VirtualMesh");
        System.out.println("ReadString -> " + name);
        
        initMesh();
        
        //Read vertices
        int vertCount = capsule.readInt("Vertex Count", 0);
        positions.clear();
        for(int i = 0; i < vertCount; i++){
            float x = capsule.readFloat("Vertex " + i + "X Coord", 0f);
            float y = capsule.readFloat("Vertex " + i + "Y Coord", 0f);
            float z = capsule.readFloat("Vertex " + i + "Z Coord", 0f);
            
            positions.add(new Vector3f(x,y,z));
        }
        
        //Write number of triangle indices
        indexList.clear();
        int indexCount = capsule.readInt("Index count", 0);
        
        //3 indices to a triangle
        int t = 0;
        int counter = 0;
        for(int i=0;i < indexCount ;i++){
            short index = capsule.readShort("Triangle " + t + ": index " + i, (short)0);
            this.indexList.add(index);
            counter++;
            if(counter == 2){
                t++;
                counter = 0;
            }
        }
        
        //Read Normals
        //Read number of normals
        int normalsCount = capsule.readInt("Normals Count", 0);
        normals.clear();
        for(int i=0;i < normalsCount; i++){
            //Write normals to file
            float x = capsule.readFloat("Normal " + i + "X Coord", 0f);
            float y = capsule.readFloat("Normal " + i + "Y Coord", 0f);
            float z = capsule.readFloat("Normal " + i + "Z Coord", 0f);
            
            normals.add(x);
            normals.add(y);
            normals.add(z);
        }
        
        tCoords.clear();
        int tCoordsCount = capsule.readInt("TCoords count", 0);
        if(tCoordsCount > 0){
            for(int i=0; i < tCoordsCount;i++){
                //Write texture coordinates to file
                float x = capsule.readFloat("TCoord " + i + "X Coord", 0f);
                float y = capsule.readFloat("TCoord " + i + "Y Coord", 0f);
                
                tCoords.add( new Vector2f(x,y));
            }
        }
    }
}

