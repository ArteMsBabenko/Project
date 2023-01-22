package tests;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import dao.UserRepository;
import domain.AccessLevel;
import domain.User;
import service.MailSender;
import service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserServiceTests {
	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private MailSender mailSender;
	@MockBean
	private PasswordEncoder passwordEncoder;

	@Test
	public void addUserTest() {
		User user = new User();
		user.setEmail("some@gmail.com");

		boolean isUserCreated = userService.addUser(user);

		Assert.assertTrue(isUserCreated);
		Assert.assertFalse(user.isActive());
		Assert.assertNotNull(user.getActivationCode());
		Assert.assertTrue(CoreMatchers.is(user.getAccessLevels()).matches(Collections.singleton(AccessLevel.USER)));

		Mockito.verify(userRepository, Mockito.times(1)).save(user);
		Mockito.verify(mailSender, Mockito.times(1))
			.send(
				ArgumentMatchers.eq(user.getEmail()),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString()
			);
	}
	
	@Test
	public void addUserFailTest() {
		User user = new User();
		user.setEmail("some@gmail.com");

		Mockito.doReturn(new User()).when(userRepository).findByEmail("some@gmail.com");

		boolean isUserCreated = userService.addUser(user);

		Assert.assertFalse(isUserCreated);

		Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
		Mockito.verify(mailSender, Mockito.times(0))
			.send(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString()
			);
	}

	@Test
	public void activateUserTest() {
		User user = new User();
		user.setActivationCode("some activation code");

		Mockito.doReturn(user).when(userRepository).findByActivationCode("activation code");

		boolean isUserActivated = userService.activateUser("activation code");

		Assert.assertTrue(isUserActivated);
		Assert.assertTrue(user.isActive());
		Assert.assertNull(user.getActivationCode());

		Mockito.verify(userRepository, Mockito.times(1)).save(user);
	}

	@Test
	public void activateUserFailTest() {
		boolean isUserActivated = userService.activateUser("activation code");

		Assert.assertFalse(isUserActivated);

		Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
	}
	
	@Test
	public void saveUserTest() {
		User user = new User("Jhon", "Jhonx", "Jhonx@gmail.com", "123456", true, new HashSet<AccessLevel>(Collections.singleton(AccessLevel.USER)));
		Map<String, String> form = new HashMap<String, String>() {{
			put("Name", "Jhon");
			put("Surname", "Jhonx");
			put("ADMIN", "ADMIN");
		}};		
		
		userService.saveUser(user, form);
		
		Assert.assertTrue(user.getName().equals("Jhon"));
		Assert.assertTrue(user.getsurname().equals("Jhonx"));
		Assert.assertFalse(user.isActive());
		Assert.assertFalse(user.getAccessLevels().contains(AccessLevel.USER));
		Assert.assertTrue(user.getAccessLevels().contains(AccessLevel.ADMIN));
		
		Mockito.verify(userRepository, Mockito.times(1)).save(user);		
	}
}
