#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec4 normal;
layout (location = 2) in vec4 color;

//matrix
uniform mat4 modelmatrix;
uniform mat4 viewmatrix;
uniform mat4 projection;

//light parameters
uniform vec3 lightPos;
uniform vec3 ambientColor; 
uniform vec3 diffuseColor;
uniform vec3 speclarColor;
uniform float kA, kD, kS, sN;

//vertex color
smooth out vec4 theColor;

smooth out vec4 thePosition;
smooth out vec4 theNormal;

void main()
{
    mat4 modelView = viewmatrix * modelmatrix;
    mat4 normalMatrix = transpose(inverse(modelView));

    // final vertex position
    gl_Position = projection * modelView * position;

    theColor = color;
    thePosition = position;
    theNormal = normal;
}