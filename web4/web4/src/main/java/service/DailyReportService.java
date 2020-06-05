package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }

    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession()).getLastReport();
    }

    public void deleteAllDailyReports(){
        new DailyReportDao(sessionFactory.openSession()).deleteAll();
    }

    public void newDay(){
        new DailyReportDao(sessionFactory.openSession()).newDay();
    }

    public void addCarToReport(Long price) {
    DailyReport dailyReport =  new DailyReportDao(sessionFactory.openSession()).getCurrentReport();
    if(dailyReport == null) {
        new DailyReportDao(sessionFactory.openSession()).newDay();
        dailyReport = new DailyReportDao(sessionFactory.openSession()).getCurrentReport();
    }
        long id = dailyReport.getId();
        long earnings = dailyReport.getEarnings() + price;
        long soldCars = dailyReport.getSoldCars() + 1;
        new DailyReportDao(sessionFactory.openSession()).updateReport(id, earnings, soldCars);
    }

    }
