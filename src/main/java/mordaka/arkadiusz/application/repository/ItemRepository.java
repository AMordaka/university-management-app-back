package mordaka.arkadiusz.application.repository;

import mordaka.arkadiusz.application.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Override
    Optional<Item> findById(Long id);

    Optional<Item> findBySubjectName(String subjectName);
}
