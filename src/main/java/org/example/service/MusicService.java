package org.example.service;

import org.example.model.Music;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MusicService implements IMusicService {
    private final List<Music> music;

    public MusicService() {
        music = new ArrayList<>();
    }

    @Override
    public List<Music> findAll() {
        return music;
    }

    @Override
    public void save(Music music) {
        this.music.add(music);
    }

    @Override
    public Music findById(int id) {
        return music.get(id);
    }

    @Override
    public void update(int id, Music music) {
        int index = this.music.indexOf(findById(id));
        this.music.set(index, music);
    }

    @Override
    public void remove(int id) {
        music.remove(findById(id));
    }
    @Override
    public boolean isValidFileExtension(String fileName) {
        String[] validExtensions = { ".mp3", ".wav", ".ogg", ".m4p" };
        for (String ext : validExtensions) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
