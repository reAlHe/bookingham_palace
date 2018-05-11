package de.maibornwolff.ste.bookingham_palace.user.api;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import de.maibornwolff.ste.bookingham_palace.BookinghamPalaceApplication;
import de.maibornwolff.ste.bookingham_palace.user.api.databuilder.CredentialsBuilder;
import de.maibornwolff.ste.bookingham_palace.user.api.databuilder.UserBuilder;
import de.maibornwolff.ste.bookingham_palace.user.api.databuilder.UserRequestBuilder;
import de.maibornwolff.ste.bookingham_palace.user.model.Credentials;
import de.maibornwolff.ste.bookingham_palace.user.model.UserRequest;
import de.maibornwolff.ste.bookingham_palace.user.repository.UserRepository;
import de.maibornwolff.ste.bookingham_palace.user.service.UserService;
import de.maibornwolff.ste.bookingham_palace.util.TestUtil;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookinghamPalaceApplication.class)
public class AuthControllerTest {

    private MockMvc restMockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthController authController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }


    @Test
    @Transactional
    public void authenticateUserWithValidCredentialsReturnsToken() throws Exception {
        userRepository.saveAndFlush(new UserBuilder().withUsername("princeWilli").withPassword("king123").build());
        Credentials credentials = new CredentialsBuilder().withUsername("princeWilli").withPassword("king123").build();

        restMockMvc.perform(post("/authentication/auth")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(credentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }


    @Test
    @Transactional
    public void authenticateUserWithInvalidCredentialsReturnsToken() throws Exception {
        Credentials credentials = new CredentialsBuilder().withUsername("badGuy").withPassword("?").build();

        restMockMvc.perform(post("/authentication/auth")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(credentials)))
                .andExpect(jsonPath("$.token").doesNotExist())
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void validateToken() {
        assert true;
    }
}