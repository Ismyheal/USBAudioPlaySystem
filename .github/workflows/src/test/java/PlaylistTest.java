import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    @Test
    void testAddMp3() {
        Playlist p = new Playlist();
        p.add(new AudioFile("song.mp3"));

        assertEquals(1, p.tracks.size());
    }

    @Test
    void testRejectVideo() {
        Playlist p = new Playlist();
        p.add(new AudioFile("video.avi"));

        assertEquals(0, p.tracks.size());
    }
}
