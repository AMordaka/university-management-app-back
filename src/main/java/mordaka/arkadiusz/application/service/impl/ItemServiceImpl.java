package mordaka.arkadiusz.application.service.impl;

import mordaka.arkadiusz.application.exception.AppException;
import mordaka.arkadiusz.application.exception.ResourceNotFoundException;
import mordaka.arkadiusz.application.model.Item;
import mordaka.arkadiusz.application.model.User;
import mordaka.arkadiusz.application.payload.ApiResponse;
import mordaka.arkadiusz.application.payload.ItemProfile;
import mordaka.arkadiusz.application.repository.ItemRepository;
import mordaka.arkadiusz.application.service.ItemService;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;

    public ItemServiceImpl(UserService userService, ItemRepository itemRepository) {
        this.userService = userService;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemProfile> getParticipatesItems(String username) {
        User user = userService.findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getStudent().getItems()) {
            itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), item.getGrade(), item.getTeacher().getUser().getName() + " " + item.getTeacher().getUser().getSurname(), item.getTeacher().getUser().getUsername()));
        }
        return itemProfiles;
    }

    @Override
    public List<ItemProfile> getCarriedItems(String username) {
        User user = userService.findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getTeacher().getCarriedItems()) {
            itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), item.getGrade(), item.getStudent().getUser().getName() + " " + item.getStudent().getUser().getSurname(), item.getStudent().getUser().getUsername()));
        }
        return itemProfiles;
    }

    @Override
    public ResponseEntity<?> putGrade(Long courseId, String studentUsername, String teacherUsername, String grade) {
        Item course = itemRepository.findById(courseId).orElseThrow(() -> new AppException("Course not found!"));
        User student = userService.findUser(studentUsername);
        User teacher = userService.findUser(teacherUsername);
        course.setStudent(student.getStudent());
        course.setTeacher(teacher.getTeacher());
        course.setGrade(grade);
        Item result = itemRepository.save(course);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getSubjectName()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User updated successfully"));

    }
}
