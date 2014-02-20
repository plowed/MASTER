#version 130

in vec3 position;
in vec3 normal;

out vec3 fColor;

uniform mat4 projection;
uniform mat4 transform;

uniform vec3 lightPosition;
uniform vec3 diffuseColor;

void main() {

  vec4 clipPosition=vec4(position,1); // coordonnées homogènes
  
  vec3 N = normal.xyz;
  N = normalize(N);

  clipPosition=transform*clipPosition;
  clipPosition=projection*clipPosition; // transformation par la matrice de projection
  
  
  vec3 L = clipPosition.xyz/clipPosition.w-lightPosition-position;
  L = normalize(L);
  
  float intensity = max(dot(L,N),0.0);
  fColor=intensity*diffuseColor;
  
  gl_Position=clipPosition; // gl_Position = clip coordinates
  
}