#version 150 core

uniform mat4 viewProjectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform vec4 colorVector;
uniform float lightFactor;

in vec3 in_Position;
in vec3 in_Normal;

out vec4 pass_Color;

void main(void) {

	gl_Position = viewProjectionMatrix * modelMatrix * vec4(in_Position, 1.0);
	
	// vertex normals are oriented in face coordinates, so they must be rotated as well
	vec4 normal = viewMatrix * modelMatrix * vec4(in_Normal, 0.0); 
	    
	// fake ambient by taking only percent of diffuse light
	float intensity = mix(1.0, dot(normal, vec4(0.0, 0.0, 1.0, 0.0)), lightFactor);

	pass_Color = colorVector*intensity;
}