package pl.edu.pjwstk.jaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.jaz.Entities.PhotoEntity;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
    public List<PhotoEntity> findAll();
}
