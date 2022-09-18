package bo.custom;

import bo.SuperBO;
import dto.UserDto;

import java.sql.SQLException;

public interface LoginBo extends SuperBO {

    String loginUser(UserDto login) throws SQLException, ClassNotFoundException;

}
