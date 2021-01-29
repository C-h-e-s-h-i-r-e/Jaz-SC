package pl.edu.pjwstk.jaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.Entities.Auction_ParameterEntity;

@Repository
public interface Auction_ParamaterRepository extends JpaRepository<Auction_ParameterEntity, Long> {
}
