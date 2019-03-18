package ca.mcgill.ecse321.cooperator;

import ca.mcgill.ecse321.cooperator.dao.CoopPositionRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.dao.UserEntityRepository;
import ca.mcgill.ecse321.cooperator.model.CoopPosition;
import ca.mcgill.ecse321.cooperator.model.ProgramManager;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.model.TermInstructor;
import ca.mcgill.ecse321.cooperator.model.UserEntity;
import ca.mcgill.ecse321.cooperator.services.CoopPositionService;
import ca.mcgill.ecse321.cooperator.services.StudentService;
import ca.mcgill.ecse321.cooperator.services.UserEntityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CooperatorUserEntityTest {

	@Mock
	private UserEntityRepository userEntityDao;

	@Mock
	private StudentRepository studentDao;

	@Mock
	private CoopPositionRepository cpDao;

	@InjectMocks
	private UserEntityService userEntityService;

	@InjectMocks
	private CoopPositionService coopPositionService;

	@InjectMocks
	private StudentService studentService;

	private ProgramManager pm;
	private static final String firstName = "Hello";
	private static final String lastName = "World";
	private static final String email = "bill.gates@gatesfoundation.com";

	private TermInstructor ti1;
	private static final String email2 = "IamSteveJobs@gmail.com";
	private static final String firstName2 = "Jeff";
	private static final String lastName2 = "The legend";
	private static final String password = "Not My Password";
	private static final String WRONG_EMAIL = "Wrong@Email.com";

	private TermInstructor ti2;
	private static final String email3 = "IamJobs@gmail.com";
	private static final String firstName3 = "Jef";
	private static final String lastName3 = "Ok";

	private CoopPosition cp1;
	private static final Integer COOP_KEY = 10;
	private static final Date startDate = new Date(5);
	private static final Date endDate = new Date(10);

	private CoopPosition cp2;
	private static final Integer COOP_KEY2 = 10;
	private static final Date startDate2 = new Date(5);
	
	
	private Student student_past;
	private static final Integer STUDENT_KEY1 = 10;

	private Student student_present;
	private static final Integer STUDENT_KEY2 = 11;
	


	private List<UserEntity> expectedList = new ArrayList<UserEntity>();
	private Set<CoopPosition> cps_ti1 = new HashSet<>();
	private Set<CoopPosition> cps_ti2 = new HashSet<>();

	@Before
	public void setMockOutput() {
		when(userEntityDao.findUserEntityByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(email)) {
				UserEntity user = new ProgramManager();
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setPassword(password);
				return user;
			} else if (invocation.getArgument(0).equals(email2)) {
				UserEntity user = new TermInstructor();
				user.setFirstName(firstName2);
				user.setLastName(lastName2);
				user.setEmail(email2);
				user.setPassword(password);
				return user;
			} else if (invocation.getArgument(0).equals(email3)) {
				UserEntity user = new TermInstructor();
				user.setFirstName(firstName3);
				user.setLastName(lastName3);
				user.setEmail(email3);
				user.setPassword(password);
				return user;
			} else {
				return null;
			}

		});

		when(studentDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STUDENT_KEY1)) {
				Student student = new Student();
				student.setStudentID(STUDENT_KEY1);
				return student;
			} else if (invocation.getArgument(0).equals(STUDENT_KEY2)) {
				Student student = new Student();
				student.setStudentID(STUDENT_KEY2);
				return student;
			} else {
				return null;
			}

		});

		when(cpDao.findByCoopId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(COOP_KEY)) {
				CoopPosition cp = new CoopPosition();
				cp.setCoopId(COOP_KEY);
				return cp;

			} else if (invocation.getArgument(0).equals(COOP_KEY2)) {
				CoopPosition cp = new CoopPosition();
				cp.setCoopId(COOP_KEY2);
				return cp;

			} else {
				return null;
			}
		});
	}

	@Before
	public void setUpMocks() {
		// program manager
		pm = mock(ProgramManager.class);
		pm = userEntityService.createProgramManager(firstName, lastName, email, password);
		expectedList.add(pm);

		// term instructor with past coop
		ti1 = mock(TermInstructor.class);
		ti1 = userEntityService.createTermInstructor(firstName2, lastName2, email2, password);
		expectedList.add(ti1);

		// term instructor with on going coop
		ti2 = mock(TermInstructor.class);
		ti2 = userEntityService.createTermInstructor(firstName3, lastName3, email3, password);
		expectedList.add(ti2);

		// student that has no on going coop
		student_past = mock(Student.class);
		student_past = studentService.createStudent(firstName, lastName);

		// student that has on going coop
		student_present = mock(Student.class);
		student_present = studentService.createStudent(firstName, lastName);

		// coop 1 is a past coop position
		cp1 = mock(CoopPosition.class);
		cp1 = coopPositionService.createCoopPosition(startDate, endDate, "asdfas", "dafsd", "fdasfa", student_past);
		cps_ti1.add(cp1);

		// coop 2 is an on going coop position
		Date currentDate= new Date();
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusYears(1).plusMonths(1).plusDays(1);
		Date endDate2 = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		cp2 = mock(CoopPosition.class);
		cp2 = coopPositionService.createCoopPosition(startDate2, endDate2, "asdfad", "asdfas", "asdfadsf", student_present);
		cps_ti2.add(cp2);

		// assigning coop set to the term instructor
		userEntityService.assignCoopToInstructor(ti1, cps_ti1);
		userEntityService.assignCoopToInstructor(ti2, cps_ti2);
	}

	@Test
	public void testUserEntityCreation() {
		assertNotNull(pm);
		assertNotNull(ti1);
		assertNotNull(ti2);
	}

	@Test
	public void testUserEntityQueryFound() {
		assertEquals(email, userEntityService.getUserEntityByEmail(email).getEmail());
		assertEquals(email2, userEntityService.getUserEntityByEmail(email2).getEmail());
		assertEquals(email3, userEntityService.getUserEntityByEmail(email3).getEmail());
	}

	@Test
	public void testUserEntityQueryNotFound() {
		assertNull(userEntityService.getUserEntityByEmail(WRONG_EMAIL));
	}

	@Test
	public void testUserEntityDeletion() {
		// deleting program manager
		assertEquals(true, userEntityService.deleteUserEntity(email));

		// deleting term instructor that has past coop
		assertEquals(true, userEntityService.deleteUserEntity(email2));

	}

//	@Test(expected = IllegalStateException.class)
//	public void testTermInstructorIllegalDeletion() {
//		// deleting term instructor that has on going coop
//		userEntityService.deleteUserEntity(email3);
//	}

}