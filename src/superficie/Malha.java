package superficie;


import java.util.ArrayList;
import util.math.Vector4f;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author André
 */
public class Malha {
    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;
    
    protected int nverts = 3;
    protected int nfaces = 1;
    
    public Malha(){
        positions = new ArrayList<>();
        colors    = new ArrayList<>();
        
        // Fill the vertices
        positions.add( new Vector4f(-0.2500000f, -0.2500000f, 0.000000f, 0.100000f) ); 
        positions.add( new Vector4f(0.000000f,-0.250000f,0.000000f,1.00000f)); 
        positions.add( new Vector4f(-0.2500000f,-0.250000f,-0.2500000f,1.000000f)); 
       
        // Fill the colors
        colors.add( new Vector4f( 0.0f, 1.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 0.0f, 1.0f, 0.0f, 1.0f) ); 
    }
}
