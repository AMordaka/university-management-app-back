package mordaka.arkadiusz.application.service;

import mordaka.arkadiusz.application.payload.ItemProfile;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {

    List<ItemProfile> getParticipatesItems(String username);

    List<ItemProfile> getCarriedItems(String username);

    ResponseEntity<?> putGrade(Long courseId, String studentUsername, String teacherUsername, String grade);
}
