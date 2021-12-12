package fact.it.artistservice.controller;

import fact.it.artistservice.model.Artist;
import fact.it.artistservice.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @PostConstruct
    public void fillDB(){
        if(artistRepository.count() == 0){
            artistRepository.save(new Artist("SuperFest 2021", "Noisia", 20, 00));
            artistRepository.save(new Artist("SuperFest 2021", "Sub Focus", 21, 30));
            artistRepository.save(new Artist("SuperFest 2021", "Madux", 23, 0));

            artistRepository.save(new Artist("Bumble 2022", "Teddy Killerz", 20, 00));
            artistRepository.save(new Artist("Bumble 2022", "Dimension", 21, 30));
            artistRepository.save(new Artist("Bumble 2022", "Break", 23, 0));
        }

        System.out.println("SF21 test: " + artistRepository.findArtistsByEvent("SuperFest 2021").size());
        System.out.println("B22 test: " + artistRepository.findArtistsByEvent("Bumble 2022").size());

        System.out.println("Noisia test: " + artistRepository.findArtistsByArtist("Noisia").size());
        System.out.println("Teddy Killerz test: " + artistRepository.findArtistsByArtist("Teddy Killerz").size());
    }

    @GetMapping("/artists/event/{eventName}")
    public List<Artist> getArtistsByEvent(@PathVariable String eventName){
        return artistRepository.findArtistsByEvent(eventName);
    }

    @GetMapping("/artists/{artistName}")
    public List<Artist> getArtistsByArtist(@PathVariable String artistName){
        return artistRepository.findArtistsByArtist(artistName);
    }

    @GetMapping("/artists/{artistName}/event/{eventName}")
    public Artist getArtistByArtistAndEvent(@PathVariable String artistName, @PathVariable String eventName){
        return artistRepository.findArtistByArtistAndEvent(artistName, eventName);
    }

    @PostMapping("/artists")
    public Artist addArtist(@RequestBody Artist artist){
        artistRepository.save(artist);
        return artist;
    }

    @PutMapping("/artists")
    public Artist updateArtist(@RequestBody Artist updatedArtist){
        Artist retrievedArtist = artistRepository.findArtistByArtistAndEvent(updatedArtist.getArtist(), updatedArtist.getEvent());

        retrievedArtist.setHour(updatedArtist.getHour());
        retrievedArtist.setMinute(updatedArtist.getMinute());

        artistRepository.save(retrievedArtist);

        return retrievedArtist;
    }

    @DeleteMapping("/artists/{artistName}/event/{eventName}")
    public ResponseEntity deleteArtist(@PathVariable String artistName, @PathVariable String eventName){
        Artist artist = artistRepository.findArtistByArtistAndEvent(artistName, eventName);

        if(artist != null){
            artistRepository.delete(artist);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
