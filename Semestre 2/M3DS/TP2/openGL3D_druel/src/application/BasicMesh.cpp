#include "BasicMesh.h"
#include "ObjLoader.h"
#include <vector>
#include <iostream>

using namespace std;
using namespace p3d;

BasicMesh::~BasicMesh() {
  glDeleteBuffers(1,&_attributeBuffer);
  glDeleteBuffers(1,&_elementBuffer);
}


BasicMesh::BasicMesh() {
  _attributeBuffer=0;
  _elementBuffer=0;
}



void BasicMesh::updateBuffer() {

}




void BasicMesh::initTetrahedron() {

    /* <AvantQ5> *

  std::vector<float> position={
    -10,0,-10, // 0
    10,0,-10,  // 1
    0,10,-20,  // 2
    0,-10,-20  // 3
  };

  std::vector<float> color={
    1,0,0,
    0,1,0,
    0,0,1,
    0,1,1
  };

  _element={
    0,1,2,0,2,3,0,1,3,1,2,3
  };
   /* </AvantQ5> */


   /* <ApresQ5> */
  std::vector<float> position={
      -10,0,-10, // 0' = 0
      10,0,-10,  // 1' = 1
      0,10,-20,  // 2' = 2

      -10,0,-10, // 3' = 0
      0,10,-20,  // 4' = 2
      0,-10,-20, // 5' = 3

      -10,0,-10, // etc
      10,0,-10,
      0,-10,-20,

      10,0,-10,
      0,10,-20,
      0,-10,-20
    };


        /* <Q5> *

      std::vector<float> color={
         1,0,0,
         1,0,0,
         1,0,0,
         0,1,0,
         0,1,0,
         0,1,0,
         0,0,1,
         0,0,1,
         0,0,1,
         1,0,1,
         1,0,1,
         1,0,1
       };

       /* </Q5> */

      /* <Q6+> */
   std::vector<float> color={
       1,0,0,
       1,0,0,
       1,0,0,
       0,1,0,
       0,1,0,
       0,1,0,
       0,0,1,
       0,0,1,
       0,0,1,
       0,1,1,
       0,1,1,
       0,1,1
     };
    /* </Q6+> */

    _element={
      0,1,2,3,4,5,6,7,8,9,10,11
    };
 /* </ApresQ5> */


  _attribute.clear();
  for(unsigned int i=0;i<position.size()/3;++i) {


   /* <AvantQ9> *
    _attribute.push_back(position[i*3+0]*0.09); // *0.09 pour être visible entre [-0.9,0.9]
    _attribute.push_back(position[i*3+1]*0.09);
    _attribute.push_back(position[i*3+2]*0.2+3.0); // report du depth entre [-1,1]
   /* </AvantQ9> */


    /* <ApresQ9> */
    _attribute.push_back(position[i*3+0]);
    _attribute.push_back(position[i*3+1]);
    _attribute.push_back(position[i*3+2]);
    /* </ApresQ9> */

    _attribute.push_back(color[i*3+0]);
    _attribute.push_back(color[i*3+1]);
    _attribute.push_back(color[i*3+2]);

  }
}


void BasicMesh::initObj(const ObjLoader &obj) {
    _attribute.clear();
    _element.clear();

    // initialisation de _attribute :
    // - obj.nbVertex() = nombre de sommets
    // - obj.position(i) = position du i-ème sommet de type Vector3 => obj.position(i).x() donne le x, .y() donne le y, etc
    // - obj.normal(i) = normale du i-ème sommet de type Vector3
    for(unsigned int i=0;i<obj.nbVertex();++i) {

        // TODO
    /* <Question 16+> */

        _attribute.push_back(obj.position(i).x());
        _attribute.push_back(obj.position(i).y());
        _attribute.push_back(obj.position(i).z());

        /* 16 & 20+ */
        _attribute.push_back(obj.normal(i).x());
        _attribute.push_back(obj.normal(i).y());
        _attribute.push_back(obj.normal(i).z());
        /* */
        /* 17 *
        _attribute.push_back( (obj.normal(i).x()+1.0)/2.0);
        _attribute.push_back( (obj.normal(i).y()+1.0)/2.0);
        _attribute.push_back( (obj.normal(i).z()+1.0)/2.0);
        /* */

    /* </Question 16+> */

    }

    // initialisation de _element (i.e. indices des triangles pour glDrawElements) :
    // - obj.nbFace() = nombre de triangles
    // - obj.indexVertex(i,j) = donne l'indice du j-ième sommet (j=0,1 ou 2) du i-ème triangle
    for(unsigned int i=0;i<obj.nbFace();i++) {

        // TODO
     /* <Question 16+> */
        _element.push_back(obj.indexVertex(i,0));
        _element.push_back(obj.indexVertex(i,1));
        _element.push_back(obj.indexVertex(i,2));
    /* </Question 16+> */
    }
}

void BasicMesh::initBuffer() {
  glGenBuffers(1,&_attributeBuffer);
  glBindBuffer(GL_ARRAY_BUFFER,_attributeBuffer);
  glBufferData(GL_ARRAY_BUFFER,_attribute.size()*sizeof(float),_attribute.data(),GL_STATIC_DRAW);


  glGenBuffers(1,&_elementBuffer);
  glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,_elementBuffer);
  glBufferData(GL_ELEMENT_ARRAY_BUFFER,_element.size()*sizeof(unsigned int),_element.data(),GL_STATIC_DRAW);
}

void BasicMesh::initVAO() {

  glGenVertexArrays(1,&_vao);
  glBindVertexArray(_vao);
  glBindBuffer(GL_ARRAY_BUFFER,_attributeBuffer);


  // TODO

  /* <Q3> */
  glVertexAttribPointer(0,3,GL_FLOAT,GL_FALSE,sizeof(float)*6,0);
  glBindBuffer(GL_ARRAY_BUFFER, _attributeBuffer);
  glVertexAttribPointer(1,3,GL_FLOAT,GL_FALSE,sizeof(float)*6,(void*)(3*sizeof(float)));
  /* </Q3> */

  glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,_elementBuffer);

  /* <Q3> */
  glEnableVertexAttribArray(0);
  glEnableVertexAttribArray(1);
  /* </Q3> */

  glBindVertexArray(0);
}

void BasicMesh::initDraw() {
  initBuffer();
  initVAO();
}


void BasicMesh::draw() {
  glBindVertexArray(_vao);
  glDrawElements(GL_TRIANGLES,_element.size(),GL_UNSIGNED_INT,(const GLvoid *)(0*sizeof(GLuint)));
  glBindVertexArray(0);
}

