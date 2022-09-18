package bo.custom.impl;

import bo.custom.UserBo;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.UserDto;
import entity.User;
import javafx.collections.ObservableList;
import view.tm.UserTM;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean addUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(userDto.getUserName(),userDto.getPassword(),userDto.getName(),userDto.getRole()));
    }

    @Override
    public ObservableList<UserTM> getUserList() throws SQLException, ClassNotFoundException {
        return userDAO.getList();
    }

    @Override
    public boolean updateUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(userDto.getUserName(),userDto.getPassword(),userDto.getName(),userDto.getRole()));
    }

    @Override
    public boolean deleteUser(String text) throws SQLException, ClassNotFoundException {
        return userDAO.delete(text);
    }
}
