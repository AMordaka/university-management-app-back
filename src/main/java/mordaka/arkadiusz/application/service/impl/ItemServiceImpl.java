package mordaka.arkadiusz.application.service.impl;

import com.opencsv.CSVReader;
import mordaka.arkadiusz.application.exception.AppException;
import mordaka.arkadiusz.application.model.*;
import mordaka.arkadiusz.application.payload.ApiResponse;
import mordaka.arkadiusz.application.payload.CourseInfo;
import mordaka.arkadiusz.application.payload.ItemProfile;
import mordaka.arkadiusz.application.payload.UserProfile;
import mordaka.arkadiusz.application.repository.ItemRepository;
import mordaka.arkadiusz.application.repository.PdfRepository;
import mordaka.arkadiusz.application.repository.RoleRepository;
import mordaka.arkadiusz.application.service.ItemService;
import mordaka.arkadiusz.application.service.MailSenderService;
import mordaka.arkadiusz.application.service.UserService;
import mordaka.arkadiusz.application.util.CSVUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final RoleRepository roleRepository;
    private final MailSenderService mailSenderService;
    private final PdfRepository pdfRepository;

    public ItemServiceImpl(UserService userService, ItemRepository itemRepository, RoleRepository roleRepository, MailSenderService mailSenderService, PdfRepository pdfRepository) {
        this.userService = userService;
        this.itemRepository = itemRepository;
        this.roleRepository = roleRepository;
        this.mailSenderService = mailSenderService;
        this.pdfRepository = pdfRepository;
    }

    @Override
    public List<ItemProfile> getParticipatesItems(String username) {
        User user = userService.findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (ItemStudent item : user.getStudent().getItems()) {
            itemProfiles.add(new ItemProfile(item.getItemStudentId(), item.getItem().getSubjectName(), item.getGrade(), item.getItem().getTeacher().getUser().getName() + " " + item.getItem().getTeacher().getUser().getSurname(), item.getItem().getTeacher().getUser().getUsername()));
        }
        return itemProfiles;
    }

    @Override
    public List<ItemProfile> getCarriedItems(String username) {
        User user = userService.findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getTeacher().getCarriedItems()) {
            if (item.getStudent() != null) {
                itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), "", "", ""));
            }
        }
        return itemProfiles;
    }

    @Override
    public ResponseEntity<?> putGrade(Long courseId, CourseInfo courseInfo) {
        Item course = itemRepository.findById(courseId).orElseThrow(() -> new AppException("Course not found!"));
        User student = userService.findUser(courseInfo.getStudentUsername());
        for (ItemStudent itemStudent : course.getStudent()) {
            if (itemStudent.getStudent() == student.getStudent()) {
                itemStudent.setGrade(courseInfo.getGrade());
                //mailSenderService.sendEmail(itemStudent.getStudent().getUser().getEmail(), "Ocena", course.getSubjectName() + " " + courseInfo.getGrade());
            }
        }
        itemRepository.save(course);

        return ResponseEntity.ok().body(new ApiResponse(true, "Grade added successfully"));
    }

    @Override
    public ResponseEntity<?> addCourse(String courseName, String teacherUsername) {
        Item course = new Item(courseName, userService.findUser(teacherUsername).getTeacher());
        itemRepository.save(course);
        return ResponseEntity.ok().body(new ApiResponse(true, "Course created successfully"));
    }

    @Override
    public List<ItemProfile> getCarriedItem(String courseName, String username) {
        User user = userService.findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getTeacher().getCarriedItems().stream().filter(item -> courseName.equalsIgnoreCase(item.getSubjectName())).collect(Collectors.toList())) {
            for (ItemStudent itemStudent : item.getStudent()) {
                itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), itemStudent.getGrade(), itemStudent.getStudent().getUser().getName() + " " + itemStudent.getStudent().getUser().getSurname(), itemStudent.getStudent().getUser().getUsername()));
            }
        }
        return itemProfiles;
    }

    @Override
    public ResponseEntity<?> assignStudentToCourse(String courseName, List<String> assigned) {
        Item course = findItemByCourseName(courseName);
        for (String username : assigned) {
            User user = userService.findUser(username);
            ItemStudent itemStudent = new ItemStudent();
            itemStudent.setItem(course);
            itemStudent.setStudent(user.getStudent());
            course.getStudent().add(itemStudent);
            itemRepository.save(course);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "Student Assigned successfully"));
    }

    private Item findItemByCourseName(String courseName) {
        return itemRepository.findBySubjectName(courseName).orElseThrow(() -> new AppException("Course not found!"));
    }

    @Override
    public List<UserProfile> getAllStudents(String courseName) {
        List<UserProfile> userProfiles = new ArrayList<>();
        Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT).orElseThrow(() -> new AppException("ERROR"));
        List<User> students = userService.findAllUsers().stream().filter(user -> (user.getRoles().contains(studentRole))).collect(Collectors.toList());
        students.removeAll(getUsersInCourse(courseName));
        for (User user : students) {
            userProfiles.add(new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getCreatedAt(), user.getUpdatedAt(), user.getStreet(), user.getNumberStreet(), user.getPostalCode(), user.getCity(), user.getRoles()));
        }
        return userProfiles;
    }

    @Override
    public ResponseEntity<?> addPdf(String name, String courseName, MultipartFile file) {
        if (file != null) {
            Item item = itemRepository.findBySubjectName(courseName).orElseThrow(() -> new AppException("Course not exist"));
            savePdfInDataBase(name, courseName, file);
            processPdf(file, item);

            return ResponseEntity.ok("Pdf added");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No File!");
    }

    private List<User> getUsersInCourse(String courseName) {
        List<User> studentsInCourse = new ArrayList<>();
        for (ItemStudent itemStudent : findItemByCourseName(courseName).getStudent()) {
            studentsInCourse.add(itemStudent.getStudent().getUser());
        }
        return studentsInCourse;
    }

    private void savePdfInDataBase(String name, String courseName, MultipartFile file) {
        try {
            User user = userService.findUser(name);
            Pdf pdf = new Pdf();
            pdf.setContent(file.getBytes());
            pdf.setFileName(name + "_" + courseName);
            pdf.setUser(user);
            pdfRepository.save(pdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processPdf(MultipartFile file, Item course) {
        CSVReader reader = new CSVUtils(file).getReader();
        try {
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (ItemStudent itemStudent : course.getStudent()) {
                    if (itemStudent.getStudent() == userService.findUser(line[0]).getStudent()) {
                        itemStudent.setGrade(line[1]);
                        itemRepository.save(course);
                        //mailSenderService.sendEmail(itemStudent.getStudent().getUser().getEmail(), "Ocena", course.getSubjectName() + " " + courseInfo.getGrade());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
