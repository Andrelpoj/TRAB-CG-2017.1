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
    protected ArrayList<Vector4f> colors = new ArrayList<>();
    protected ArrayList<Vector4f> positions = new ArrayList<>();
    
    
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
    
    //retorna o ponto medio caso os pontos que delimitam a aresta tenham sinais diferentes
    private Vector4f testaAresta(Vector4f p1, Vector4f p2, boolean s1, boolean s2){
        if( (s1&&(!s2)) || ((!s1)&&s2) ){
            Vector4f pontoMedio = new Vector4f();
            pontoMedio.x = (p1.x + p2.x)/2;
            pontoMedio.y = (p1.y + p2.y)/2;
            pontoMedio.z = (p1.z + p2.z)/2;
            pontoMedio.w = 1.0f;
            
            return pontoMedio;
        }
        
        return null;
    }
    
    public Surface(ArrayList<Tetraedro> listaTetra){
        
        //fill the vertices
        for(int i=0;i<listaTetra.size();i++){
            //testa as aretas do tetraedro
            Tetraedro tetra = listaTetra.get(i);
            
            ArrayList<Vector4f> listaAux = new ArrayList<>();
            Vector4f aux;
            
            aux = testaAresta(tetra.a,tetra.b,tetra.sinais[0],tetra.sinais[1]);
            if(aux!=null) listaAux.add(aux);
            aux = testaAresta(tetra.b,tetra.c,tetra.sinais[1],tetra.sinais[2]);
            if(aux!=null) listaAux.add(aux);
            aux = testaAresta(tetra.c,tetra.a,tetra.sinais[2],tetra.sinais[0]);
            if(aux!=null) listaAux.add(aux);
            
            aux = testaAresta(tetra.a,tetra.d,tetra.sinais[0],tetra.sinais[3]);
            if(aux!=null) listaAux.add(aux);
            aux = testaAresta(tetra.b,tetra.d,tetra.sinais[1],tetra.sinais[3]);
            if(aux!=null) listaAux.add(aux);
            aux = testaAresta(tetra.c,tetra.d,tetra.sinais[2],tetra.sinais[3]);
            if(aux!=null) listaAux.add(aux);
            
            if(listaAux.size()==3){
                positions.add(listaAux.get(0));
                positions.add(listaAux.get(1));
                positions.add(listaAux.get(2));
            }
            
            if(listaAux.size()==4){
                positions.add(listaAux.get(0));
                positions.add(listaAux.get(1));
                positions.add(listaAux.get(2));
                
                positions.add(listaAux.get(0));
                positions.add(listaAux.get(2));
                positions.add(listaAux.get(3));
            }
        }
        
        // Fill the colors
        for(int c=0;c<positions.size();c++){
            colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) );
        }
        
    }
    
    
}
