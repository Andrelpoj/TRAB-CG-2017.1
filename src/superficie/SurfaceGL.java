/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superficie;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.shader.ObjectGL;
import util.shader.ShaderProgram;

/**
 *
 * @author André
 */
public class SurfaceGL extends Surface implements ObjectGL{
    // Vertex Array Object Id
    private int vaoHandle;
    // Shader Program
    private ShaderProgram shader;
    // Buffer with the Positions
    private FloatBuffer positionBuffer;
    
    // Buffer with the Positions
    private FloatBuffer normalBuffer;
 
    // Buffer with the Colors
    private FloatBuffer colorBuffer;
    
    private int nTriangulos;
    
    public SurfaceGL(ArrayList<Tetraedro> listaTetra){
        super(listaTetra);
        nTriangulos = 3 * positions.size();
    }
    
    @Override
    public void fillVAOs() {
        // fills the VBOs
        fillVBOs();

        // create vertex byffer object (VBO) for vertices
        int positionBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        // create vertex byffer object (VBO) for normals
        int normalBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);

        // create VBO for color values
        int colorBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // create vertex array object (VAO)
        vaoHandle = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // assign vertex VBO to slot 0 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 1 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 2 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, 0, 0);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
 
    @Override
    public void loadShaders() {
        // compile and link vertex and fragment shaders into
        // a "program" that resides in the OpenGL driver
        shader = new ShaderProgram();

        // do the heavy lifting of loading, compiling and linking
        // the two shaders into a usable shader program
        shader.init("shaders/phong.vert", "shaders/phong.frag");
 
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());
    }
    
    public void setFloat(String nameFloat, float dataFloat){        
        // defines the uniform variable
        int fltId = GL20.glGetUniformLocation(shader.getProgramId(), nameFloat);
        GL20.glUniform1f(fltId, dataFloat);   
    }

    public void setVector(String nameVector, Vector3f dataVector) {
        // Buffer with the Model Matrix
        FloatBuffer vecBuff = BufferUtils.createFloatBuffer(3);

        // converts from matrix to FloatBuffer
        vecBuff.clear();
        dataVector.store(vecBuff);
        vecBuff.flip();
        
        // defines the uniform variable
        int vecId = GL20.glGetUniformLocation(shader.getProgramId(), nameVector);
        GL20.glUniform3(vecId, vecBuff);
    }
    
    public void setMatrix(String nameMatrix, Matrix4f dataMatrix) {
        // Buffer with the Model Matrix
        FloatBuffer matBuff = BufferUtils.createFloatBuffer(16);

        // converts from matrix to FloatBuffer
        matBuff.clear();
        dataMatrix.store(matBuff);
        matBuff.flip();
        
        // defines the uniform variable
        int matrixId = GL20.glGetUniformLocation(shader.getProgramId(), nameMatrix);
        GL20.glUniformMatrix4(matrixId, false, matBuff);
    }

    @Override
    public void render() {
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());

        // bind vertex and color data
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0); // VertexPosition
        GL20.glEnableVertexAttribArray(1); // VertexNormal
        GL20.glEnableVertexAttribArray(2); // VertexColor

        // draw VAO
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3*nTriangulos);
    }
    
    @Override
    public void fillVBOs() {
        // convert vertex array to buffer
        //positionBuffer = BufferUtils.createFloatBuffer(4 * 3 * (positions.size()/3)); //4(coordinates)*3(vertices)*n(triangles)
        positionBuffer = BufferUtils.createFloatBuffer(4 * positions.size()); //4(coordinates * n(vertices)
        
        // convert vertex array to buffer
        normalBuffer = BufferUtils.createFloatBuffer(4 * positions.size()); //4(coordinates)*n(vertices)
        
        // convert color array to buffer
        colorBuffer = BufferUtils.createFloatBuffer(4 * colors.size()); //4(coordinates)*n(vertices)

        for(int i=0;i<positions.size();i++){
            positions.get(i).store(positionBuffer);
            positions.get(i).store(normalBuffer);
        }
        for(int j=0;j<colors.size();j++){
            colors.get(j).store(colorBuffer);
        }
        
        positionBuffer.flip();
        normalBuffer.flip();
        colorBuffer.flip();
    }

    
}
