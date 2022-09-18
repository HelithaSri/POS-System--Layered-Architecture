package dao.custom;

import dao.CrudDAO;
import dto.UserDto;
import entity.User;
import view.tm.UserTM;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User, String, UserTM> {
    String login(User login) throws SQLException, ClassNotFoundException;
}
