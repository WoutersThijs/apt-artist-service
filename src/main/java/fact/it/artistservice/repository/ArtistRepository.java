package fact.it.artistservice.repository;

import fact.it.artistservice.model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArtistRepository extends MongoRepository<Artist, String> {
    List<Artist> findArtistsByEvent(String eventName);
    List<Artist> findArtistsByArtist(String artistName);
    Artist findArtistByArtistAndEvent(String artistName, String eventName);
}
