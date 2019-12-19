# JavaPT: A Simple Java Path Tracer

A simple multithreaded Java path tracer partially based on [smallpt](https://www.kevinbeason.com/smallpt) and [SWPathTracer](https://github.com/capagot/swpathtracer).

## Sampe Rendered Image ##

![Sample image](https://github.com/capagot/javapt/blob/development/sample/image.png)

## Primitives and Materials ##
In the current state the renderer supports only spherical primitives and the following BSDFs:

* Perfectly diffuse (Lambertian).
* Smooth conductors.
* Smooth dielectrics.

## Implementation Overview ##
The renderer implements brute force forward (camera to light) path tracing, where each ray has a maximum depth level of 10. Pixels are sampled uniformly and the resulting radiance is averaged according to the number of samples (*i.e.* a box-filter).

The following command line options are supported:

* ```-w```: image width in pixels;
* ```-h```: image height in pixels;
* ```-s```: number fo pixel samples;
* ```-f```: Json input filename containing the scene description;
* ```-t```: number of rendering threads.

The generated image is stored in the PPM format.

## Building the Renderer and Rendering the Sample##

In order to build the renderer, clone the repo, and in the project root directore issue:

* ```$ mvn compile```
* ```$ mvn package```

To render the sample, one can issue the following command at the project root directory:

```java -jar target/javapt-0.1.0.jar -help -w 400 -h 300 -s 64  -f samples/cornell.json  -t 4```
