package mordaka.arkadiusz.application.repository;

import mordaka.arkadiusz.application.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
