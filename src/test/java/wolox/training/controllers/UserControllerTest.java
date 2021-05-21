package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@WebMvcTest(UserController.class)
@WithMockUser
public class UserControllerTest {

    private static final String url = "/api/users";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    private User user;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        user = new User();
        user.setUserName("jose.delvecchio");
        user.setName("Jose");
        user.setBirthdate(LocalDate.now().minusYears(26L));
        user.setPassword("123");
    }

    @Test
    public void whenFindOneWhichExists_thenUserIsReturned() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        mvc.perform(get(url.concat("/1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName",is("jose.delvecchio")));
    }

    @Test
    public void whenFindAllUsers_thenReturnList() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void whenFindOneWhichNonExists_thenExceptionIsThrown() throws Exception {
        mvc.perform(get(url.concat("/10")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    public void whenCreateUser_thenReturnUserAdded() throws Exception {

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

}
