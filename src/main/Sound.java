package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.net.URL;

// clasa pentru gestionarea sunetelor
public class Sound {
    Clip clip;
    URL[] soundURL =  new URL[5];

    // constructor pentru initializarea sunetelor
    public Sound(){
        // initializare URL-uri pentru sunetele disponibile
        soundURL[0]= getClass().getResource("/sound/forestismagicbun.wav");
        soundURL[1]= getClass().getResource("/sound/gmae.wav");
        soundURL[2]= getClass().getResource("/sound/Victory.wav");
    }

    // metoda pentru setarea fisierului audio
    public void setFile(int i){
        try{
            // incarcarea fisierului audio de la URL-ul specificat
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            // crearea unui clip pentru fisierul audio
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e){
            // gestionarea exceptiilor
        }
    }

    // metoda pentru redarea sunetului
    public void play(){
        clip.start();
    }

    // metoda pentru redarea continua a sunetului
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // metoda pentru oprirea sunetului
    public void stop(){
        clip.stop();
    }
}
