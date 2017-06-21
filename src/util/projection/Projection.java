package util.projection;

import util.math.FastMath;
import util.math.Matrix4f;

/**
 *
 * @author mlage
 */
public class Projection {
    
    private float fovY   = 0.0f;
    private float aspect = 0.0f;
    private float zNear  = 0.0f;
    private float zFar   = 0.0f;
    
    private float top  = 0.0f;
    private float bottom   = 0.0f;
    private float left  = 0.0f;
    private float right   = 0.0f;
    
    
    public Projection(float fovY, float aspect, float zNear, float zFar,
                        float bottom, float top, float left, float right){
    //public Projection(float fovY, float aspect, float zNear, float zFar){    
        this.fovY   = fovY;
        this.aspect = aspect;
        
        this.zNear  = zNear;
        this.zFar   = zFar;
        
        this.top = top;
        this.bottom = bottom;
        this.right = right;
        this.left = left;
        
        /*
        float angle = fovY / 2.0f * FastMath.DEG_TO_RAD;
        float tangent = FastMath.sin(angle) / FastMath.cos(angle);
        
        this.top = zNear*tangent;
        this.bottom = -top;
        this.right = top * aspect;
        this.left = -right;
        */      
    }
    
    public Matrix4f ortographic(){
        
        Matrix4f tempMatrix = new Matrix4f();
        tempMatrix.m11 = 2/(right-left); tempMatrix.m21 = 0; tempMatrix.m31 = 0; tempMatrix.m41 = -(left+right)/(right-left);
        tempMatrix.m12 = 0; tempMatrix.m22 = 2/(top-bottom); tempMatrix.m32 = 0; tempMatrix.m42 = -(top+bottom)/(top-bottom);
        tempMatrix.m13 = 0; tempMatrix.m23 = 0; tempMatrix.m33 = -2/(zFar-zNear); tempMatrix.m43 = -(zFar+zNear)/(zFar-zNear);
        tempMatrix.m14 = 0; tempMatrix.m24 = 0; tempMatrix.m34 = 0; tempMatrix.m44 = 1;
        
        return tempMatrix;
    }   
    
    public Matrix4f perspective() {
        float angle = fovY / 2.0f * FastMath.DEG_TO_RAD;
        float cotangent = FastMath.cos(angle) / FastMath.sin(angle);

        float e = cotangent / aspect; // focal length of the camera

        float fpn = zFar + zNear;  // far plus near
        float fmn = zFar - zNear;  // far minus near

        Matrix4f tempMatrix = new Matrix4f();
        tempMatrix.m11 = e;     tempMatrix.m21 = 0.0f;       tempMatrix.m31 = 0.0f;        tempMatrix.m41 = 0.0f;
        tempMatrix.m12 = 0.0f;  tempMatrix.m22 = cotangent;  tempMatrix.m32 = 0.0f;        tempMatrix.m42 = 0.0f;
        tempMatrix.m13 = 0.0f;  tempMatrix.m23 = 0.0f;       tempMatrix.m33 = -(fpn/fmn);  tempMatrix.m43 = -2.0f * zNear * zFar / fmn;
        tempMatrix.m14 = 0.0f;  tempMatrix.m24 = 0.0f;       tempMatrix.m34 = -1.0f;       tempMatrix.m44 = 0.0f;

        return tempMatrix;
    }

}
