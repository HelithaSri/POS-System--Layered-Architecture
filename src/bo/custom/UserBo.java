package bo.custom;

import bo.SuperBO;
import dto.UserDto;
import javafx.collections.ObservableList;
import view.tm.UserTM;

import java.sql.SQLException;

public interface UserBo extends SuperBO {
    boolean addUser(UserDto userDto) throws SQLException, ClassNotFoundException;

    ObservableList<UserTM> getUserList() throws SQLException, ClassNotFoundException;

    boolean updateUser(UserDto userDto) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String text) throws SQLException, ClassNotFoundException;
}
