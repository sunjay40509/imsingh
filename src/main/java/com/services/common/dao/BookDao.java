package com.services.common.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.services.common.utils.HibernateUtil;
import com.services.model.Book;
import com.services.model.User;

public class BookDao implements SuperDaoInterface {
	
	
	private Logger logger = Logger.getLogger(this.getClass());
	protected SessionFactory sessionFactory;
	protected SuperDao model;
	
	public BookDao() {
		super();
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	@Override
	public <T> int insert(T object) {
		Session session = null;
		Transaction tx = null;
		int returnId;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			returnId = (Integer) session.save(object);
			tx.commit();
			// MyApplication.HEADER_SCREEN.notify("Successfully Saved Data",
			// NotificationType.SUCCESS);
		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				logger.error(rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return returnId;
	}

	public <T> List<T> findAllBook() {

		Session session = null;
		Transaction tx = null;
		List list;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class);
			list = criteria.list();
			tx.commit();
		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				System.out.println(rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		HibernateUtil.shutdown();

		return list;

	}

	@Override
	public <T> List<T> select(String userName) {
		// Session session = sessionFactory.openSession();
		//
		// List<T> results;
		// try{
		// session.beginTransaction();
		// Query query = session.createQuery(hql);
		// results = query.list();
		// session.getTransaction().commit();
		// }finally{
		// session.close();
		// }
		//
		// a a

		Session session = null;
		Transaction tx = null;
		List list;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);

			criteria.add(Restrictions.eq("userName", userName));
			list = criteria.list();
			tx.commit();
		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				System.out.println(rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		// Session session = null;
		// Transaction tx = null;
		// List<T> results;
		// try {
		// session = HibernateUtil.getSessionFactory().openSession();
		// tx = session.beginTransaction();
		// tx.setTimeout(5);
		//
		// Query query = session.createQuery(hql);
		// results = query.list();
		// tx.commit();
		//
		// } catch (RuntimeException e) {
		// try {
		// tx.rollback();
		// } catch (RuntimeException rbe) {
		// logger.error("Couldn’t roll back transaction", rbe);
		// }
		// throw e;
		// } finally {
		// if (session != null) {
		// session.close();
		// }
		// }

		HibernateUtil.shutdown();
		// logger.info("Select query result :: " + results);

		return list;

	}

	public void updateNew(String hql) {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.getTransaction();

			tx.setTimeout(5);

			Query query = session.createQuery(hql);
			query.executeUpdate();
			tx.commit();

		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				logger.error("Couldn’t roll back transaction", rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		HibernateUtil.shutdown();
		// logger.info("Select query result :: " + results);

	}

	@Override
	public <T> Boolean update(String hql) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		int result = query.executeUpdate();
		logger.info(result);
		session.getTransaction().commit();
		session.close();
		HibernateUtil.shutdown();
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public <T> void insertOrUpdate(T object) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(object);

			tx.commit();

		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				logger.error(rbe);
				// ExceptionUtils.showException(rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public <T> void delete(T object) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.delete(object);

			tx.commit();

		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				logger.error(rbe);
				// ExceptionUtils.showException(rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
}
