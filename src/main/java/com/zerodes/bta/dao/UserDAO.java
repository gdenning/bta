package com.zerodes.bta.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.zerodes.bta.domain.User;

public interface UserDAO extends JpaDao<User> {

	User findUserByPrimaryKey(long userId) throws DataAccessException;

	User findUserByEmail(String email) throws DataAccessException;

	Set<User> findUserByName(String name) throws DataAccessException;

	Set<User> findAllUsers() throws DataAccessException;
}