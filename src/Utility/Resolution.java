/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.HashMap;

/**
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class Resolution{
    public int width,height;
    public int[] aspect;
    static HashMap<String,Resolution> resMap;

    static {
        resMap = new HashMap();
        resMap.put("CGA", new Resolution(320,200,8,5));
        resMap.put("QVGA", new Resolution(320,240,4,3));
        resMap.put("VGA [NTSC]", new Resolution(640,480,4,3));
        resMap.put("WVGA", new Resolution(800,480,5,3));
        
        resMap.put("WVGA [NTSC - 480]", new Resolution(854,480,16,9));
        resMap.put("XGA", new Resolution(1024,768,4,3));
        resMap.put("SVGA", new Resolution(800,600,4,3));
    }
    
    public Resolution(){
        aspect = new int[2];
    }

    public Resolution(int w, int h){
        this();
        width = w;
        height = h;
    }

    public Resolution(int w, int h, int ax, int ay){
        this(w,h);
        aspect[0] = ax;
        aspect[1] = ay;
    }

}