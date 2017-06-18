#version 330

smooth in vec4 theColor;

smooth in vec4 thePosition;
smooth in vec4 theNormal;

out vec4 outputColor;

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

void main()
{   
    mat4 modelView = viewmatrix * modelmatrix;
    mat4 normalMatrix = transpose(inverse(modelView));

    vec4 positionWorld = modelmatrix * thePosition;
    vec4 newNormal = normalize(normalMatrix * theNormal);

    //diffuse
    vec3 lightDir = normalize(lightPos - positionWorld.xyz);
    float iD = max(0.0, dot(lightDir, newNormal.xyz));

    //specular
    vec3  v  = -normalize((modelView * thePosition).xyz);
    vec3  h  =  normalize(lightDir + v);
    float iS =  pow(max(0.0, dot(newNormal.xyz, h)), sN);

    vec3 lightFactor = kA * ambientColor + kD * iD * diffuseColor + kS * iS * speclarColor;

    outputColor = vec4(theColor.rgb * lightFactor, theColor.a);
}