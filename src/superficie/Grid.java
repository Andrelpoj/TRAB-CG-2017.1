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
public class Grid {
    private final float xMin = -5.0f; 
    private final float xMax = 5.0f;
    private final float yMin = -5.0f; 
    private final float yMax = 5.0f;
    private final float zMin = -5.0f; 
    private final float zMax = 5.0f;
    
    private int nDivisoes;
    private int nCubos = nDivisoes * nDivisoes * nDivisoes;
    
    //private int nVerts = nCubos * 8;
    private int nTetra = nCubos * 6;
    
    private ArrayList<Cubo> listaCubos = new ArrayList<>(nCubos); 
    private ArrayList<Tetraedro> listaTetra = new ArrayList<>(nTetra);
    
    public Grid(int n){
        nDivisoes = n;
        nCubos = nDivisoes * nDivisoes * nDivisoes;
        //nVerts = nCubos * 8;
        nTetra = nCubos * 6;
        
        criaListaCubos();
        testaPontos(listaCubos);
        
        criaListaTetra(listaCubos);
    }
    
    public ArrayList<Cubo> getListaCubos(){
        return this.listaCubos;
    }
    
    public ArrayList<Tetraedro> getListaTetra(){
        return this.listaTetra;
    }
    
    private float movimento(float Max, float Min, int indice){
        return indice * ( (Max - Min)/nDivisoes );  
    }
    
    private void criaListaCubos(){
        Vector4f pontoInicial = new Vector4f(xMin,yMin,zMin,1.0f);
        
        float vX = movimento(xMax,xMin,1);
        float vY = movimento(yMax,yMin,1);
        float vZ = movimento(zMax,zMin,1);
        
        //int count = 0;
        
        for(int i=0;i<nDivisoes;i++){
            for(int j=0;j<nDivisoes;j++){
                for(int k=0;k<nDivisoes;k++){
                    Vector4f pontoAtual = new Vector4f();
                    //pontoAtual.x = pontoInicial.x + ( i * ((xMax-xMin)/(nDivisoes)) ); 
                    
                    /*
                    Vector4f vX = new Vector4f(movimento(xMax,xMin,i),0,0,0);
                    Vector4f vY = new Vector4f(0,movimento(yMax,yMin,j),0,0);
                    Vector4f vZ = new Vector4f(0,0,movimento(zMax,zMin,k),0);
                    */
                    
                    pontoAtual.x = pontoInicial.x + vX*i;
                    pontoAtual.y = pontoInicial.y + vY*j;
                    pontoAtual.z = pontoInicial.z + vZ*k;
                    pontoAtual.w = 1.0f;
                    
                    Cubo c = new Cubo(pontoAtual, vX, vY, vZ);
                    listaCubos.add(c);
                    
                    //count++;
                }
            }
        }
        //System.out.println(count);
        //System.out.println(listaCubos.size());
    }
    
    
    private void testaPontos(ArrayList<Cubo> listaCubos){
        for(int i=0;i<listaCubos.size();i++){
            for(int j=0;j<Cubo.nverts;j++){
                Cubo atual = listaCubos.get(i);
                atual.sinais[j] = Surface.testaSinal(atual.positions.get(j));
            }
        }
    }
    
    private void criaListaTetra(ArrayList<Cubo> listaCubos){
        for(int i=0;i<listaCubos.size();i++){
            Cubo cuboAtual = listaCubos.get(i);
            
            //Criacao dos Tetraedros
            Vector4f p0 = cuboAtual.positions.get(0);
            Vector4f p1 = cuboAtual.positions.get(1);
            Vector4f p2 = cuboAtual.positions.get(2);
            Vector4f p3 = cuboAtual.positions.get(3);
            Vector4f p4 = cuboAtual.positions.get(4);
            Vector4f p5 = cuboAtual.positions.get(5);
            Vector4f p6 = cuboAtual.positions.get(6);
            Vector4f p7 = cuboAtual.positions.get(7);
            
            boolean s0 = cuboAtual.sinais[0];
            boolean s1 = cuboAtual.sinais[1];
            boolean s2 = cuboAtual.sinais[2];
            boolean s3 = cuboAtual.sinais[3];
            boolean s4 = cuboAtual.sinais[4];
            boolean s5 = cuboAtual.sinais[5];
            boolean s6 = cuboAtual.sinais[6];
            boolean s7 = cuboAtual.sinais[7];
            
            
            Tetraedro t1 = new Tetraedro(p0, p3, p4, p2,
                                         s0, s3, s4, s2);
            Tetraedro t2 = new Tetraedro(p3, p7, p4, p2,
                                         s3, s7, s4, s2);
            Tetraedro t3 = new Tetraedro(p1, p0, p4, p2,
                                         s1, s0, s4, s2);
            Tetraedro t4 = new Tetraedro(p5, p1, p4, p2,
                                         s5, s1, s4, s2);
            Tetraedro t5 = new Tetraedro(p7, p6, p4, p2,
                                         s7, s6, s4, s2);
            Tetraedro t6 = new Tetraedro(p6, p5, p4, p2,
                                         s6, s5, s4, s2);            
            
            /*
            Tetraedro t1 = new Tetraedro(p3, p0, p4, p2,
                                         s3, s0, s4, s2);
            Tetraedro t2 = new Tetraedro(p3, p7, p4, p2,
                                         s3, s7, s4, s2);
            Tetraedro t3 = new Tetraedro(p1, p0, p4, p2,
                                         s1, s0, s4, s2);
            Tetraedro t4 = new Tetraedro(p1, p5, p4, p2,
                                         s1, s5, s4, s2);
            Tetraedro t5 = new Tetraedro(p6, p7, p4, p2,
                                         s6, s7, s4, s2);
            Tetraedro t6 = new Tetraedro(p6, p5, p4, p2,
                                         s6, s5, s4, s2);            
            */
            
            listaTetra.add(t1);
            listaTetra.add(t2);
            listaTetra.add(t3);
            listaTetra.add(t4);
            listaTetra.add(t5);
            listaTetra.add(t6);
        }
    }
    
    public float getXMin(){
        return xMin;
    }
    
    public float getXMax(){
        return xMax;
    }
    
    public float getYMin(){
        return yMin;
    }
    
    public float getYMax(){
        return yMax;
    }
    
    public float getZMin(){
        return zMin;
    }
    
    public float getZMax(){
        return zMax;
    }
}
