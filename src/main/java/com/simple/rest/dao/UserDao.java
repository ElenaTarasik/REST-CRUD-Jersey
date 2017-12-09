package com.simple.rest.dao;

import java.util.List;
import javax.persistence.Query;
import org.hibernate.*;
import com.simple.rest.model.User;
import com.simple.rest.util.HibernateUtil;

public class UserDao {

	private static SessionFactory session = HibernateUtil.getSessionFactory();

	public List<User> getUsers() {
		Session ss = session.openSession();
		List<User> users = ss.createQuery("from User").list();
		ss.close();
		return users;
	}

	public void addUser(User user) throws Exception {
		Session ss = session.openSession();
		ss.beginTransaction();
		ss.save(user);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateUser(long id, User user) throws Exception {
		getUser(id);
		Session ss = session.openSession();
		ss.beginTransaction();
		Query query = ss.createQuery(
				"update User u set u.name =:newName, " + "u.age =:newAge, u.gender =:newGender where u.id =:paramId");
		query.setParameter("newName", user.getName());
		query.setParameter("newAge", user.getAge());
		query.setParameter("newGender", user.getGender());
		query.setParameter("paramId", id);
		query.executeUpdate();
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteUser(long id) throws Exception {
		User user = getUser(id);
		Session ss = session.openSession();
		ss.beginTransaction();
		ss.delete(user);
		ss.getTransaction().commit();
		ss.close();
	}

	public User getUser(long id) throws Exception {
		Session ss = session.openSession();
		Query query = ss.createQuery("from User u where u.id =:paramId");
		query.setParameter("paramId", id);
		User user = (User) query.getSingleResult();
		ss.close();
		return user;
	}
}
