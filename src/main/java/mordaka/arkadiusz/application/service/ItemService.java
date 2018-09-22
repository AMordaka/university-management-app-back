package mordaka.arkadiusz.application.service;

import mordaka.arkadiusz.application.model.Item;
import mordaka.arkadiusz.application.payload.CourseInfo;
import mordaka.arkadiusz.application.payload.ItemProfile;
import mordaka.arkadiusz.application.payload.UserProfile;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {

    List<ItemProfile> getParticipatesItems(String username);

    List<ItemProfile> getCarriedItems(String username);

    ResponseEntity<?> putGrade(Long courseId, CourseInfo courseInfo);

    ResponseEntity<?> addCourse(String courseName, String teacherUsername);

    List<ItemProfile> getCarriedItem(String courseName, String username);

    ResponseEntity<?>  assignStudentToCourse(String courseName, List<String> assigned);

    List<UserProfile> getAllStudents(String courseName);
}
