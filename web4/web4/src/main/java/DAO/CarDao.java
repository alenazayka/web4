package DAO;

import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }
    public List<Car> getAllCars() {
        List<Car> cars = session.createQuery("FROM Car").list();
        session.close();
     return cars;
    }

    public Car presenceOfTheCar(String brand, String model, String licensePlate){
        Query query = session.createQuery("from Car where brand = :br and model = :md and licensePlate = :lp");
        query.setParameter("br", brand);
        query.setParameter("md", model);
        query.setParameter("lp", licensePlate);
        Car car = (Car) query.uniqueResult();
        session.close();
        if (car == null) {
            return null;
        }
        return car;
    }


    public void deleteCar(long id) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE Car WHERE id = :id");
        query.setParameter("id", id).executeUpdate();
        transaction.commit();
        session.close();
    }

    public long quantityOfCarsByBrand(String brand) {
        Query query = session.createQuery("SELECT COUNT(*) FROM Car WHERE brand = :brand");
        query.setParameter("brand", brand);
        long quantityOfCarsByBrand = (long) query.uniqueResult();
        session.close();
        return quantityOfCarsByBrand;
    }

    public void deleteAll(){
        session.createQuery("DELETE From Car").executeUpdate();
        session.close();
    }

    public void addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
    }
}
