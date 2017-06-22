/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superficie;

import java.util.ArrayList;
import util.math.FastMath;
import util.math.Vector4f;

/**
 *
 * @author André
 */
public class Surface {
    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;
    
    
    //esfera
    // x^2 + y^2 + z^2 = raio
    static final float raio = 2.0f;
    public static boolean testaSinal(Vector4f vertice){
        float c = FastMath.pow(vertice.x, 2) + FastMath.pow(vertice.y, 2) + FastMath.pow(vertice.z, 2) ; 
        float valor = FastMath.pow(raio, 2);
        if(c <= valor )
            return true;
        else
            return false;
    }
    
    // Fill the colors
    //colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) );
}
