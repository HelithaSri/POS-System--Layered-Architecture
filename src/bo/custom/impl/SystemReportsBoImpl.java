package bo.custom.impl;

import bo.custom.SystemReportsBo;
import dao.DAOFactory;
import dao.custom.QueryDAO;
import dto.ItemDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class SystemReportsBoImpl implements SystemReportsBo {
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ObservableList<ItemDto> getMostPopulerItemsToday() throws SQLException, ClassNotFoundException {
        return queryDAO.getMostPopulerItemsToday();
    }

    @Override
    public ObservableList<ItemDto> getMostPopulerItemsMonth() throws SQLException, ClassNotFoundException {
        return queryDAO.getMostPopulerItemsMonth();
    }

    @Override
    public ObservableList<ItemDto> getMostPopulerItemsYear() throws SQLException, ClassNotFoundException {
        return queryDAO.getMostPopulerItemsYear();
    }
}
