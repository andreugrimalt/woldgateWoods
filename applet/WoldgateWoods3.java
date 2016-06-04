import processing.core.*; 
import processing.xml.*; 

import javax.sound.midi.*; 
import javax.sound.midi.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class WoldgateWoods3 extends PApplet {

 
float [][] matrixR = new float [2][9999];
float [][] matrixG = new float [2][9999];
float [][] matrixB = new float [2][9999];
int i=0;
int velocity;
long duration;
float j,k;
PImage img;
int b = color (0,0,0);
SimpleMidiSynth synth;

public void setup() {
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
public void draw() { 

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
public void readFileR(String filename) {

  String rows[] = loadStrings(filename);
  for(int i = 0; i<rows.length; i++) {
    float temp[] =PApplet.parseFloat(split(rows[i],' '));
    matrixR[0][i]=temp[0];
    matrixR[1][i]=temp[1];
  }
}

// This function reads a file and store the data into a matrix.
public void readFileG(String filename) {

  String rows[] = loadStrings(filename);
  for(int i = 0; i<rows.length; i++) {
    float temp[] =PApplet.parseFloat(split(rows[i],' '));
    matrixG[0][i]=temp[0];
    matrixG[1][i]=temp[1];
  }
}

// This function reads a file and store the data into a matrix.
public void readFileB(String filename) {

  String rows[] = loadStrings(filename);
  for(int i = 0; i<rows.length; i++) {
    float temp[] =PApplet.parseFloat(split(rows[i],' '));
    matrixB[0][i]=temp[0];
    matrixB[1][i]=temp[1];
  }
}

 
class SimpleMidiSynth { 
  Synthesizer synthesizer = null; // the Java MIDI synth 
  MidiChannel channel; // the single MIDI channel 
  int note; // current playing note 
  SimpleMidiSynth() { 
    // Get synth and open it 
    try { 
      synthesizer = MidiSystem.getSynthesizer(); 
      synthesizer.open();
    } 
    catch (MidiUnavailableException e) { 
      e.printStackTrace();
    } 
    // Set our channel to the first MIDI channel 
    MidiChannel[] channels = synthesizer.getChannels(); 
    channel = channels[8];
  } 
  // Simple control change to set pan 
  public void pan(int i) { 
    channel.controlChange(10, i);
  } 
  // Select an instrument 
  public void instrument(int i) { 
    channel.programChange(0, i);
  } 
  // Panic button to kill all sounding notes 
  public void allNotesOff() { 
    channel.allNotesOff();
  } 
  // Ask this thread to halt for at least deltaT millis 
  public void mySleep(long deltaT) { 
    try { 
      Thread.sleep(deltaT);
    } 
    catch (InterruptedException e) {
    }
  } 
  // Play a note at a given velocity. The note is killed at the next call. 
    public void play(int noteIn, int vel) { 
    channel.noteOff(note); 
    channel.noteOn(noteIn, vel); 
    note = noteIn;
  } 
  // Play a note at a given velocity. The note is held for deltaT millis. 
    public void play(int note, int vel, long deltaT) { 
    channel.noteOn(note, vel); 
    mySleep(deltaT); 
    channel.noteOff(note);
  } 
  // Play a group of notes simultaneously for deltaT miilis. 
  // Set velocities[i] = 0 for any non-sounding note i in range 0 - 
    // velocities.length 
  public void play(int[] velocities, long deltaT) { 
    for (int i = 0; i < velocities.length; ++i) { 
      channel.noteOn(i, velocities[i]);
    } 
    mySleep(deltaT); 
    for (int i = 0; i < velocities.length; ++i) { 
      channel.noteOff(i);
    }
  } 
  // Close and reease resources 
  public void close() { 
    synthesizer.close();
  }
} 

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#ffffff", "WoldgateWoods3" });
  }
}
