/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superficie;

import util.math.Vector4f;

/**
 *
 * @author André
 */
public class Tetraedro {
    Vector4f a;
    Vector4f b;
    Vector4f c;
    Vector4f d;
    
    boolean[] sinais = new boolean[4];
    
    public Tetraedro(Vector4f a, Vector4f b, Vector4f c, Vector4f d,
                     boolean sA, boolean sB, boolean sC, boolean sD){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        
        sinais[0] = sA;
        sinais[1] = sB;
        sinais[2] = sC;
        sinais[3] = sD;
    }
}
