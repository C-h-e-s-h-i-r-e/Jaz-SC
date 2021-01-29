package pl.edu.pjwstk.jaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.Entities.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
}
