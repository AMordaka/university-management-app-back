package mordaka.arkadiusz.application.controller;

import mordaka.arkadiusz.application.payload.CourseInfo;
import mordaka.arkadiusz.application.payload.ItemProfile;
import mordaka.arkadiusz.application.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/student/{username}/items")
    public List<ItemProfile> getParticipatesItems(@PathVariable(value = "username") String username) {
        return itemService.getParticipatesItems(username);
    }

    @GetMapping("/teacher/{username}/items")
    public List<ItemProfile> getCarriedItems(@PathVariable(value = "username") String username) {
        return itemService.getCarriedItems(username);
    }

    @PostMapping("/{course}/putGrade")
    public ResponseEntity<?> putGrade(@PathVariable(value = "course") Long courseId, @RequestBody CourseInfo courseInfo) {
        return itemService.putGrade(courseId, courseInfo);
    }

    @PostMapping("createCourse/{course}/{username}")
    public ResponseEntity<?> createCourse(@PathVariable(value = "course") String courseName, @PathVariable(value = "username") String teacherUsername) {
        return itemService.addCourse(courseName, teacherUsername);
    }

}
