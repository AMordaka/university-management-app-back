package mordaka.arkadiusz.application.repository;

import mordaka.arkadiusz.application.model.Pdf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfRepository extends JpaRepository<Pdf, Long> {
}
