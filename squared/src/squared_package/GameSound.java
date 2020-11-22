package squared_package;
 

import java.io.File;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

 
public enum GameSound {
	
	START("start.wav"),
	CELL_CLOSED("cellaChiusa.wav"),
    TOUCH("tocco.wav");
    
	
    private String fileName;
    
    
    
    GameSound(String fname) {
        this.fileName = fname;
        
        
       
    }
     
    public Clip play() {
        Clip clip = null;
        try {
        	
        	
            AudioInputStream sound = 
                    AudioSystem.getAudioInputStream(
                    new File("sounds/"+fileName));
             
            clip = AudioSystem.getClip();

            int livVolume=GameClient.getVolume();
            clip.open(sound);
            FloatControl volume=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(livVolume);
             
            clip.addLineListener((e) -> {
                if(LineEvent.Type.STOP.equals(e.getType()))
                    e.getLine().close();
            });
             
            clip.loop(0);
             
             
             
        } catch(Exception e) {
            System.err.println("Error: Audio Fail! " + e.getMessage());
        }
         
        return clip;
    }
     

     
}