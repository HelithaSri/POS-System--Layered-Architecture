package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.UserDAO;
import dto.UserDto;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.UserTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean add(User userDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `User` VALUE (?,?,?,?)", userDto.getUserName(), userDto.getPassword(), userDto.getName(), userDto.getRole());
    }

    @Override
    public boolean delete(String userName) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `user` WHERE userName=?", userName);
    }

    @Override
    public boolean update(User userDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `User` SET password=md5(?), name=?, role=? WHERE userName=?", userDto.getPassword(), userDto.getName(), userDto.getRole(), userDto.getUserName());
    }

    @Override
    public ObservableList<UserTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> list = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `user`");
        while (rst.next()) {
            list.add(new UserTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return list;
    }

    @Override
    public String login(User login) throws SQLException, ClassNotFoundException {
        String username = login.getUserName();
        String password = login.getPassword();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `User` WHERE userName = ? AND password=md5(?)", username, password);
        if (rst.next()) {
            String userRole = rst.getString(4);
            return userRole;
        } else {
            return "";
        }
    }
}
