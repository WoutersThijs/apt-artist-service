package fact.it.artistservice.controller;

import fact.it.artistservice.model.Artist;
import fact.it.artistservice.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Artist> getArtistByArtist(@PathVariable String artistName){
        return artistRepository.findArtistsByArtist(artistName);
    }

    @PostMapping("/artists")
    public Artist addArtist(@RequestBody Artist artist){
        artistRepository.save(artist);

        return artist;
    }
}
