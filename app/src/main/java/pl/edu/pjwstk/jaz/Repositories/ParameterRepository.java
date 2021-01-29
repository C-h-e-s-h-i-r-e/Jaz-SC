package pl.edu.pjwstk.jaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.Entities.ParameterEntity;

import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<ParameterEntity,Long> {
    public Optional<ParameterEntity> findByName(String name);
}
