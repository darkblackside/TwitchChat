package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import models.User;

public class DefaultUserDAO implements IUserDAO {

	@Override
	public void saveUsers(List<User> users, String path) throws FileNotFoundException, IOException
	{
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeInt(users.size());
		
		for(User user:users)
		{
			oos.writeObject(user);
		}
		
		oos.close();
	}

	@Override
	public List<User> loadUsers(String path) throws IOException, ClassNotFoundException
	{
		List<User> users = new ArrayList<User>();
		
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		int no = ois.readInt();
		
		for(int i = 0; i < no; i++)
		{
			User u = (User) ois.readObject();
			users.add(u);
		}
		
		ois.close();
		
		return users;
	}
}
