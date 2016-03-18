package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import models.User;

public interface IUserDAO {
	public void saveUsers(List<User> s, String path) throws FileNotFoundException, IOException;
	public List<User> loadUsers(String path) throws IOException, ClassNotFoundException;
}
