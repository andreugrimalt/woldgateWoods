import javax.sound.midi.*; 
float [][] matrixR = new float [2][9999];
float [][] matrixG = new float [2][9999];
float [][] matrixB = new float [2][9999];
int i=0;
int velocity;
long duration;
float j,k;
PImage img;
color b = color (0,0,0);
SimpleMidiSynth synth;

void setup() {
  smooth(); 
  img = loadImage("a.jpg");
  
  // Make black the first rectangle. 
  // The first matrix index are [1,1], therefore there is a rectangle situated at [0,0]
  // that is not drawn. 
  for(int i=0;i<5;i++) {
    for(int j=0;j<10;j++) {
      img.set(j,i,b);
    }
  }

  background(0);
  size(794,405);
  
  // Call readFile function
  readFileR("max.dat");
  readFileG("max1.dat");
  readFileB("max2.dat");

  synth = new SimpleMidiSynth();

  img.resize(794,405);
  image(img,0,0);
} 
void draw() { 

  frameRate(300);
  noStroke();

  // Draw a rectangles over the image row by row and fill it with the right color.
  // It works when the number of divissions "d" is 100.
  j+=8;
  if(j % 99 ==0) {
    k+=4;
    j=0;
  }

  rect(j,k,10,5);
  fill((int)matrixR[1][i],(int)matrixG[1][i],(int)matrixB[1][i]);


  // The midi notes are the red color values
  int note = (int)matrixR[1][i];
  i++;

  // BACKGROUND NOISE. If no background noise is desired choose a "v" value
  // different than 0. This will make silent all the midi notes lower than "v"
  int v=0;
  if(matrixR[1][i-1]>v) {
    velocity=(int)matrixG[1][i]+50;
  }
  else {
    velocity=0;
  }


  // Stops the draw loop
  if(i==9998) {
    noLoop();
  }

  // The duration is controlled by the blue values. 
  // The higher the values, the higher will the duration be.
  long duration = round(1000*matrixB[1][i] / frameRate);

  // Play the notes
  synth.play(note, velocity, duration);
}

// This function reads a file and store the data into a matrix.
void readFileR(String filename) {

  String rows[] = loadStrings(filename);
  for(int i = 0; i<rows.length; i++) {
    float temp[] =float(split(rows[i],' '));
    matrixR[0][i]=temp[0];
    matrixR[1][i]=temp[1];
  }
}

// This function reads a file and store the data into a matrix.
void readFileG(String filename) {

  String rows[] = loadStrings(filename);
  for(int i = 0; i<rows.length; i++) {
    float temp[] =float(split(rows[i],' '));
    matrixG[0][i]=temp[0];
    matrixG[1][i]=temp[1];
  }
}

// This function reads a file and store the data into a matrix.
void readFileB(String filename) {

  String rows[] = loadStrings(filename);
  for(int i = 0; i<rows.length; i++) {
    float temp[] =float(split(rows[i],' '));
    matrixB[0][i]=temp[0];
    matrixB[1][i]=temp[1];
  }
}

