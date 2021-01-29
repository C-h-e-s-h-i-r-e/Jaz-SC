package pl.edu.pjwstk.jaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.Entities.AuctionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
    public Optional<AuctionEntity> findByTitle(String title);
    public List<AuctionEntity> findAll();
}