package cubo;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.projection.Projection;

import org.lwjgl.input.Keyboard;
import superficie.Grid;
import superficie.Surface;
import superficie.SurfaceGL;


public class Main{
    
    private static boolean orto = false;
    
    private final CubeGL cube = new CubeGL();
    
    // Creates a new surface
    Grid grid = new Grid(80);
    private SurfaceGL surface = new SurfaceGL(grid.getListaTetra());;
    
    // Animation:
    private float  currentAngleY = 0.0f;
    private float  currentAngleX = 0.0f;
    
    // Projection Matrix
    private final Projection proj = new Projection(45, 1.3333f, 0.0f, 10f,
                                                    -2.5f, 2.5f, -2.5f, 2.5f);
    //private final Projection proj = new Projection(45, 1.3333f, 0.0f, 100.0f);
        
    
    // View Matrix
    private final Vector3f eye = new Vector3f( 0.0f, 3.0f, 3.0f);
    private final Vector3f at  = new Vector3f( 0.0f, 0.0f, 0.0f);
    private final Vector3f up  = new Vector3f( 0.0f, 1.0f, 2.0f);
    
    // Camera
    private final Camera cam = new Camera(eye, at, up);

    // Light
    private final Vector3f lightPos     = new Vector3f( 0.0f, 2.0f, -2.0f);
    private final Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f speclarColor = new Vector3f(1.0f, 1.0f, 1.0f);
    
    private final float kA =  0.4f;
    private final float kD =  0.5f;
    private final float kS =  0.1f;
    private final float sN = 60.0f;
    
    // Model Matrix:
    private final Matrix4f rotationMatrixY = new Matrix4f();
    private final Matrix4f rotationMatrixX = new Matrix4f();
    private final Matrix4f scaleMatrix = new Matrix4f();
    
    // Final Matrix
    private final Matrix4f modelMatrix = new Matrix4f();
    private final Matrix4f viewMatrix  = new Matrix4f();      
    private final Matrix4f projMatrix  = new Matrix4f();
    
    /**
     * General initialization stuff for OpenGL
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        
        // width and height of window and view port
        int width = 640;
        int height = 480;

        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.create(pixelFormat, contextAtrributes);
        
        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: "   + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
         
        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
    }

    public void run() {
        
        //#############################################
        //grid = new Grid(2);
        //surface = new SurfaceGL(grid.getListaTetra());
        
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        //cube.fillVAOs();
        //cube.loadShaders();
        
        surface.fillVAOs();
        surface.loadShaders();
        
        // Model Matrix setup
        scaleMatrix.m11 = 0.5f;
        scaleMatrix.m22 = 0.5f;
        scaleMatrix.m33 = 0.5f;
        
        // light setup
        /*
        cube.setVector("lightPos"    , lightPos);
        cube.setVector("ambientColor", ambientColor);
        cube.setVector("diffuseColor", diffuseColor);
        cube.setVector("speclarColor", speclarColor);
        
        
        cube.setFloat("kA", kA);
        cube.setFloat("kD", kD);
        cube.setFloat("kS", kS);
        cube.setFloat("sN", sN);
        */
        
        surface.setVector("lightPos"    , lightPos);
        surface.setVector("ambientColor", ambientColor);
        surface.setVector("diffuseColor", diffuseColor);
        surface.setVector("speclarColor", speclarColor);
        
        surface.setFloat("kA", kA);
        surface.setFloat("kD", kD);
        surface.setFloat("kS", kS);
        surface.setFloat("sN", sN);
        
        while (Display.isCloseRequested() == false) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            //GL11.glEnable(GL11.GL_CULL_FACE);
            //GL11.glCullFace(GL11.GL_BACK);
            
            if(Keyboard.isKeyDown(Keyboard.KEY_O)){
                if(orto){
                    orto = false;
                }
                else{
                    orto = true;
                }
            }
            
            // Projection and View Matrix Setup
            if(orto == false){
                projMatrix.setTo(proj.perspective());            
            }
            else{
                projMatrix.setTo(proj.ortographic());
            }
            viewMatrix.setTo(cam.viewMatrix());
            
            if(Keyboard.isKeyDown(Keyboard.KEY_B)){
                currentAngleX -= 0.01f;
            }
            else if(Keyboard.isKeyDown(Keyboard.KEY_C)){
                currentAngleX += 0.01f;
            }
            
            if(Keyboard.isKeyDown(Keyboard.KEY_L)){
                currentAngleY -= 0.01f;
            }
            else if(Keyboard.isKeyDown(Keyboard.KEY_R)){
                currentAngleY += 0.01f; 
            }
            
            /*
            //Y-Rotation
            float c = FastMath.cos(currentAngleY);
            float s = FastMath.sin(currentAngleY);      
            rotationMatrixY.m22 = c; rotationMatrixY.m32 = -s;
            rotationMatrixY.m23 = s; rotationMatrixY.m33 = c;

            //X-Rotation
            c = FastMath.cos(currentAngleX);
            s = FastMath.sin(currentAngleX);      
            rotationMatrixX.m11 = c; rotationMatrixX.m31 = -s;
            rotationMatrixX.m13 = s; rotationMatrixX.m33 = c;
            */
            
            float c = FastMath.cos(currentAngleX);
            float s = FastMath.sin(currentAngleX);
            
            rotationMatrixX.m22 = c; rotationMatrixX.m32 = -s;
            rotationMatrixX.m23 =s; rotationMatrixX.m33 = c;
            
            c = FastMath.cos(currentAngleY);
            s = FastMath.sin(currentAngleY);
            
            rotationMatrixY.m11 = c;  rotationMatrixY.m31 = s;
            rotationMatrixY.m13 = -s; rotationMatrixY.m33 = c;
            
            
            modelMatrix.setToIdentity();
            
            modelMatrix.multiply(rotationMatrixX);
            modelMatrix.multiply(rotationMatrixY);
            
            modelMatrix.multiply(scaleMatrix);
            
            /*
            cube.setMatrix("modelmatrix", modelMatrix);
            cube.setMatrix("viewmatrix" , viewMatrix);
            cube.setMatrix("projection" , projMatrix);
            cube.render();
            */
            
            
            surface.setMatrix("modelmatrix", modelMatrix);
            surface.setMatrix("viewmatrix" , viewMatrix);
            surface.setMatrix("projection" , projMatrix);
            surface.render();
            
            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
     * main method to run the example
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        Main example = new Main();
        example.initGl();
        example.run();
    }
}
