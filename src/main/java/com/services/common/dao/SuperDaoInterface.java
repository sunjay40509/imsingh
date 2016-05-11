package com.services.common.dao;

import java.util.List;

public interface SuperDaoInterface {
	

	/**
	 * @param data
	 * 	Customized method to save data to the database.
	 */
	<T> int insert(T data);
	<T> void insertOrUpdate(T data);
	<T> List<T> select(String hql);
	<T> Boolean update(String hql);
	<T> void delete(T object);
}
