# A simple Java Path Tracer

A simple multithreaded Java path tracer partially based on [smallpt](https://www.kevinbeason.com/smallpt) and [SWPathTracer](https://github.com/capagot/swpathtracer).

# Sampe Rendered Image

![Sample image](http://https://github.com/capagot/javapt/tree/development/samples/image.png)

## Primitives and Materials ##
In the current state the renderer supports only spherical primitives and the following BSDFs:

* Perfectly diffuse (Lambertian).
* Perfectly specular.
* Dielectric.

## Implementation Overview ##
The renderer implements forward (camera to light) path tracing, where each ray has a maximum depth level of 10. Pixes are sampled uniformly, and the resuling radiance averaged according to the number of samples (box-filter).

The following command line options are accepted:

* ```-w```: image width in pixels;
* ```-h```: image height in pixels;
* ```-s```: number fo pixel samples;
* ```-f```: Json input filename containing the scene description;
* ```-t```: number of rendering threads.

The generated image is stored in the PPM format.
