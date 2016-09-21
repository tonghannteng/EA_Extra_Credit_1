package cs544.mum.edu.EA_Extra_Credit_1;

/**
 * Name: TONGHANN TENG
 * ID: 985108
 * Task: Extra Credit 1
 * Class: Application
 *
 */
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import cs544.mum.edu.model.Administrator;
import cs544.mum.edu.model.Project;
import cs544.mum.edu.model.Resource;
import cs544.mum.edu.model.Status;
import cs544.mum.edu.model.Task;
import cs544.mum.edu.model.Volunteer;

public class App {

	private static EntityManagerFactory emf;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	static {
		try {
			emf = Persistence.createEntityManagerFactory("cs544");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		createProject();
		emf.close();
	}

	private static void createProject() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();

			// Resource
			Resource resource = new Resource();
			resource.setDescription("Midterm Exam");
			em.persist(resource);

			Resource resource2 = new Resource();
			resource2.setDescription("Final Exam");
			em.persist(resource2);

			Resource resource3 = new Resource();
			resource3.setDescription("Present Project");
			em.persist(resource3);

			// Task
			Task task1 = new Task();
			task1.setStatus(Status.NEW);
			task1.setResources(Arrays.asList(resource, resource2, resource3));
			task1.setTimeFrame(5);
			task1.setDescription("New Task Implemented");
			em.persist(task1);

			// Project
			Project project = new Project();
			project.setDescription("EA Extra Credit 1");
			project.setStatus(Status.INPROGRESS);
			project.setExpectedStartDate(sdf.parse("2016-10-10"));
			project.setExpectedEndDate(sdf.parse("2016-10-11"));
			project.setLocation("Fairfield");
			project.addTask(task1);
			em.persist(project);

			// Volunteer
			Volunteer v1 = new Volunteer();
			v1.setName("Setharika");
			v1.addTask(task1);
			em.persist(v1);

			// Admin
			Administrator admin1 = new Administrator();
			admin1.setName("Tonghann");
			admin1.addProject(project);
			em.persist(admin1);

			tx.commit();
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive()))
				tx.rollback();
		} finally {
			if ((em != null) && (em.isOpen()))
				em.close();
		}
	}
}
