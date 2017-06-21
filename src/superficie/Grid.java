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
    private float xMin = -5.0f; 
    private float xMax = 5.0f;
    private float yMin = -5.0f; 
    private float yMax = 5.0f;
    private float zMin = -5.0f; 
    private float zMax = 5.0f;
    
    private int nDivisoes;
    private int nCubos = nDivisoes * nDivisoes * nDivisoes;
    
    private int nVerts = nCubos * 8;
    private int nTetra = nCubos * 6;
    
    private ArrayList<Cubo> listaCubos = new ArrayList<>(nCubos); 
    
    public Grid(int n){
        nDivisoes = n;
        nCubos = nDivisoes * nDivisoes * nDivisoes;
    
        nVerts = nCubos * 8;
        nTetra = nCubos * 6;
        
        criaListaCubos();
    }
    
    public ArrayList<Cubo> getListaCubos(){
        return this.listaCubos;
    }
    
    private float movimento(float Max, float Min, int indice){
        return indice * ( (Max - Min)/nDivisoes );  
    }
    
    private void criaListaCubos(){
        Vector4f pontoInicial = new Vector4f(xMin,yMin,zMin,1.0f);
        
        float vX = movimento(xMax,xMin,1);
        float vY = movimento(yMax,yMin,1);
        float vZ = movimento(zMax,zMin,1);
        
        int count = 0;
        
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
                    
                    count++;
                }
            }
        }
        System.out.println(count);
    }
    
}
