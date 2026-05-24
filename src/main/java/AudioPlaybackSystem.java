

import java.util.ArrayList;

/* ===================== AUDIO FILE ===================== */
class AudioFile {
    String fileName;

    AudioFile(String fileName) {
        this.fileName = fileName;
    }

    boolean isSupported() {
        return fileName.endsWith(".mp3") || fileName.endsWith(".wav");
    }
}

/* ===================== PLAYLIST ===================== */
class Playlist {
    ArrayList<AudioFile> tracks = new ArrayList<>();
    int index = 0;

    void add(AudioFile f) {
        if (f.isSupported()) tracks.add(f);
    }

    AudioFile current() {
        return tracks.get(index);
    }

    void next() {
        index = (index + 1) % tracks.size();
    }

    void previous() {
        index = (index - 1 + tracks.size()) % tracks.size();
    }
}

/* ===================== USB SERVICE ===================== */
class USBService {
    boolean connectUSB() {
        System.out.println("USB Connected");
        return true;
    }
}

/* ===================== AUDIO PLAYER ===================== */
class AudioPlayerService {
    void play(String song) {
        System.out.println("Playing: " + song);
    }

    void pause() {
        System.out.println("Paused");
    }

    void resume() {
        System.out.println("Resumed");
    }
}

/* ===================== CONTROLS ===================== */
class PlaybackControls {
    AudioPlayerService player;
    Playlist playlist;

    PlaybackControls(AudioPlayerService p, Playlist pl) {
        player = p;
        playlist = pl;
    }

    void play() {
        player.play(playlist.current().fileName);
    }

    void next() {
        playlist.next();
        play();
    }

    void previous() {
        playlist.previous();
        play();
    }

    void pause() {
        player.pause();
    }

    void resume() {
        player.resume();
    }
}

/* ===================== DISPLAY ===================== */
class PlayerDisplay {
    void show(Playlist p) {
        System.out.println("Playlist:");
        for (AudioFile f : p.tracks) {
            System.out.println("- " + f.fileName);
        }
    }
}

/* ===================== MAIN CLASS ===================== */
public class AudioPlaybackSystem {
    public static void main(String[] args) {
        USBService usb = new USBService();
        usb.connectUSB();

        Playlist playlist = new Playlist();
        playlist.add(new AudioFile("song1.mp3"));
        playlist.add(new AudioFile("song2.wav"));

        AudioPlayerService player = new AudioPlayerService();
        PlaybackControls controls = new PlaybackControls(player, playlist);
        PlayerDisplay display = new PlayerDisplay();

        display.show(playlist);
        controls.play();
        controls.next();
        controls.pause();
        controls.resume();
        controls.previous();
    }
}