package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public boolean addCar(String brand, String model, String licensePlate, long price){
        long quantityOfCars = new CarDao(sessionFactory.openSession()).quantityOfCarsByBrand(brand);
        if(quantityOfCars>=10){
            return false;
        }
        Car car = new Car(brand, model, licensePlate, price);
        new CarDao(sessionFactory.openSession()).addCar(car);
        return true;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public Car buyCar(String brand, String model, String licensePlate){
        Car car = new CarDao(sessionFactory.openSession()).presenceOfTheCar(brand, model, licensePlate);
    if(car!=null){
        new CarDao(sessionFactory.openSession()).deleteCar(car.getId());
        return car;
    }
    return null;
    }

    public void deleteAll() {
    new CarDao(sessionFactory.openSession()).deleteAll();
    }
}
