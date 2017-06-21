/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superficie;

import java.util.ArrayList;
import util.math.Vector4f;

/**
 *
 * @author André
 */
public class Cubo {
    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;
    
    protected int nverts = 8;
    protected int nfaces = 12;
    
    public Cubo(Vector4f pontoIn, float vX, float vY, float vZ){
        positions = new ArrayList<>(8);
        
        float x = pontoIn.x;
        float y = pontoIn.y;
        float z = pontoIn.z;
        
        // Fill the vertices
        //Front face
        positions.add( new Vector4f(  x    ,  y    , (z+vZ), 1.0f) );
        positions.add( new Vector4f(  x    , (y+vY), (z+vZ), 1.0f) );
        positions.add( new Vector4f( (x+vX), (y+vY), (z+vZ), 1.0f) );
        positions.add( new Vector4f( (x+vX),  y    , (z+vZ), 1.0f) );
        
        //Back face
        positions.add( new Vector4f(  x    ,  y    , z , 1.0f) );
        positions.add( new Vector4f(  x    , (y+vY), z , 1.0f) );
        positions.add( new Vector4f( (x+vX), (y+vY), z , 1.0f) );
        positions.add( new Vector4f( (x+vX),  y    , z , 1.0f) );
        
        

    }
}
