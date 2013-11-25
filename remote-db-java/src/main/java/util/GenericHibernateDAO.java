package util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

public abstract class GenericHibernateDAO<T, ID extends Serializable>
		implements GenericDAO<T, ID> {

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		Type type = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.persistentClass = (Class<T>) type;
	}

	protected Session getSession() {
		return HibernateUtil.getInstance().getSessionFactory().openSession();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().load(getPersistentClass(), id,
					LockMode.UPGRADE);
		else
			entity = (T) getSession().load(getPersistentClass(), id);

		return entity;
	}

	public List<T> findAll() {
		return findByCriteria(); 
	}


	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	public T makePersistent(T entity) {
		Session s = getSession();
		Transaction t = s.beginTransaction();

		System.out.println(entity.getClass());
		try {
			s.saveOrUpdate(entity);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		}

		return entity;
	}

	public void makeTransient(T entity) {
		Session s = getSession();
		Transaction t = s.beginTransaction();

		System.out.println(entity.getClass());
		try {
			s.delete(entity);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Session s = getSession();
		Transaction t = null;

		try {
			t = s.beginTransaction();
			Criteria crit = s.createCriteria(getPersistentClass());
			for (Criterion c : criterion) {
				crit.add(c);
			}
			t.commit();
			return crit.list();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}
		return null;
	}
	
	
	protected List<T> findByCriteria(List<Criterion> criterions) {
		Session s = getSession();
		Transaction t = null;

		try {
			t = s.beginTransaction();
			Criteria crit = s.createCriteria(getPersistentClass());
			for (Criterion c : criterions) {
				crit.add(c);
			}
			t.commit();
			return crit.list();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		}
		return null;
	}

}
