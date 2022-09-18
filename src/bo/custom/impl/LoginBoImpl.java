package bo.custom.impl;

import bo.custom.LoginBo;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.UserDto;
import entity.User;

import java.sql.SQLException;

public class LoginBoImpl implements LoginBo {
    private UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public String loginUser(UserDto login) throws SQLException, ClassNotFoundException {
        return userDAO.login(new User(login.getUserName(),login.getPassword(),login.getName(),login.getRole()));
    }
}
