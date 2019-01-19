package mordaka.arkadiusz.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import mordaka.arkadiusz.application.model.Student;
import mordaka.arkadiusz.application.model.User;
import mordaka.arkadiusz.application.payload.LoginRequest;
import mordaka.arkadiusz.application.payload.SignUpRequest;
import mordaka.arkadiusz.application.repository.UserRepository;
import mordaka.arkadiusz.application.service.UserService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void contextLoads() {
        assertThat(userRepository).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    public void shouldFindNoUsersIfRepositoryExists() {
        int size = userService.findAllUsers().size();
        assertThat(size).isZero();
    }

    @Test
    public void aNotLogged() throws Exception {

        mockMvc.perform(get("/user/{username}", "teacher").contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    public void userServiceRegisterStudentTest() {
        SignUpRequest request = new SignUpRequest();
        request.setName("Name");
        request.setSurname("Surname");
        request.setUsername("Username");
        request.setCity("City");
        request.setEmail("email@email.pl");
        request.setNumberStreet("100");
        request.setPostalCode("00-000");
        request.setStreet("Street");
        request.setPassword("Passowrd");
        assertThat(userService.registerStudent(request)).isNull();
    }


    @Test
    public void userServiceRegisterTeacherTest() {
        SignUpRequest request = new SignUpRequest();
        request.setName("Name");
        request.setSurname("Surname");
        request.setUsername("Username");
        request.setCity("City");
        request.setEmail("email@email.pl");
        request.setNumberStreet("100");
        request.setPostalCode("00-000");
        request.setStreet("Street");
        request.setPassword("Passowrd");
        assertThat(userService.registerTeacher(request)).isNull();
    }

    @Test
    public void emptyRepositoryTest() {
        assertThat(userRepository.findAll().size()).isZero();
    }


    @Test
    public void badDataUser() {
        Student student = new Student();
        User user = new User();
        user.setStudent(student);
        student.setUser(user);
        user.setPassword("password");
        assertThat(userRepository.save(user)).isNull();
    }

    @Test
    public void insertUserIntoRepository() {
        Student student = new Student();
        User user = new User("name", "surname", "test", "test", "test", "test", "test", "test", "test");
        user.setId(Long.valueOf(1));
        user.setStudent(student);
        student.setUser(user);
        user.setPassword("password");
        userRepository.save(user);
        assertThat(userRepository.findAll().size()).isZero();
    }



    @Test
    public void loginSuccessfully() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("100100");
        request.setPassword("qwerty123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(post("/api/auth/signin").contentType(
                MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isOk());
    }


    @Test
    public void httpRegisterStudent() throws Exception {
        SignUpRequest request = new SignUpRequest();
        request.setName("student");
        request.setSurname("Surname");
        request.setUsername("Username");
        request.setCity("City");
        request.setEmail("email@email.pl");
        request.setNumberStreet("100");
        request.setPostalCode("00-000");
        request.setStreet("Street");
        request.setPassword("Passowrd");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(post("/api/auth/signup").contentType(
                MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isOk());
    }

    @Test
    public void httpRegisterTeacher() throws Exception {
        SignUpRequest request = new SignUpRequest();
        request.setName("Name");
        request.setSurname("Surname");
        request.setUsername("teacher");
        request.setCity("City");
        request.setEmail("emai2l@email.pl");
        request.setNumberStreet("100");
        request.setPostalCode("00-000");
        request.setStreet("Street");
        request.setPassword("Passowrd");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(post("/api/auth/signupTeacher").contentType(
                MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isOk());
    }


    @Test
    public void logged() throws Exception {

        mockMvc.perform(get("/user/{username}", "teacher").contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
