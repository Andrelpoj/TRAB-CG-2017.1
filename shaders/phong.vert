#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec4 normal;
layout (location = 2) in vec4 color;

//matrix
uniform mat4 modelmatrix;
uniform mat4 viewmatrix;
uniform mat4 projection;

//vertex color
smooth out vec4 theColor;
out vec4 theNormal;

//normal matrix
out mat4 normalMatrix;

//object world position
out vec4 positionWorld;
out vec4 thePosition;

void main()
{
    mat4 modelView = viewmatrix * modelmatrix;
    normalMatrix = transpose(inverse(modelView));
    positionWorld = modelmatrix * position;

    // final vertex position
    gl_Position = projection * modelView * position;
    
    thePosition = modelView * position;
    theColor = color;
    theNormal = normal;
}