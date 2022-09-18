package controller;

import bo.BoFactory;
import bo.custom.SystemReportsBo;
import com.jfoenix.controls.JFXDatePicker;
import db.DbConnection;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class SystemReportsFormController {

    public PieChart pieChart;
    public PieChart pieChart2;
    public PieChart pieChart3;
    public JFXDatePicker dateStartDate;
    public JFXDatePicker dateEndDate;

    ObservableList<ItemDto> todayList = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> todayPie = FXCollections.observableArrayList();

    ObservableList<ItemDto> monthList = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> monthPie = FXCollections.observableArrayList();

    ObservableList<ItemDto> yearList = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> yearPie = FXCollections.observableArrayList();

    private final SystemReportsBo systemReportsBo = (SystemReportsBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.Report);

    public void initialize() throws SQLException, ClassNotFoundException {

        todayList = systemReportsBo.getMostPopulerItemsToday();
        for (ItemDto itemDto : todayList) {
            todayPie.add(new PieChart.Data(itemDto.getDescription(), itemDto.getQtyOnHand()));
        }
        pieChart.setData(todayPie);

        monthList = systemReportsBo.getMostPopulerItemsMonth();
        for (ItemDto itemDto : monthList) {
            monthPie.add(new PieChart.Data(itemDto.getDescription(), itemDto.getQtyOnHand()));
        }
        pieChart2.setData(monthPie);

        yearList = systemReportsBo.getMostPopulerItemsYear();
        for (ItemDto itemDto : yearList) {
            yearPie.add(new PieChart.Data(itemDto.getDescription(), itemDto.getQtyOnHand()));
        }
        pieChart3.setData(yearPie);
    }

    public void dailyReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/DailyFinancialReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null , DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void monthlyReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/MonthlyFinancialReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null , DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void annuallyReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/AnnuallyFinancialReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null , DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void customReport(MouseEvent mouseEvent) {
        LocalDate fromDateValue = dateStartDate.getValue();
        LocalDate toDateValue = dateEndDate.getValue();
        String from = String.valueOf(fromDateValue);
        String to = String.valueOf(toDateValue);
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/IncomeReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            HashMap map=new  HashMap();
            map.put("fromDate",fromDateValue);
            map.put("toDate",toDateValue);
            map.put("from",from);
            map.put("to",to);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
