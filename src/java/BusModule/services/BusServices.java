package BusModule.services;

import BusModule.dao.BusDAO;
import BusModule.model.Bus;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusServices {

    private BusDAO busDAO;

    public BusServices() {
        this.busDAO = new BusDAO();
    }

    public boolean addBus(Bus bus) throws SQLException {
        if (bus.getCapacity() <= 0) {
            throw new IllegalArgumentException("Sức chứa phải lớn hơn 0");
        }
        return busDAO.addBus(bus);
    }

    public Bus getBusById(int busId) throws SQLException {
        return busDAO.findBusById(busId);
    }

    public boolean deleteBus(int busId) throws SQLException {
        return busDAO.deleteBus(busId);
    }

    public boolean updateBus(Bus bus) throws SQLException {
        if (bus.getCapacity() <= 0) {
            throw new IllegalArgumentException("Sức chứa phải lớn hơn 0");
        }
        return busDAO.updateBus(bus);
    }

    public List<Bus> getAllBuses() throws SQLException {
        return busDAO.findAll();
    }

    public List<Bus> getBuses(String search, String sort, int page, int pageSize) throws SQLException {
        return busDAO.findAll(search, sort, page, pageSize);
    }

    public int countAllBuses() throws SQLException {
        return busDAO.countAll();
    }

    public boolean assignBusToTrip(int busId, int tripId) throws SQLException {
        return busDAO.assignBusToTrip(busId, tripId);
    }

    public boolean isPlateNumberExists(String plateNumber) throws SQLException {
        return busDAO.isPlateNumberExists(plateNumber);
    }

    public List<Bus> searchBusByPlate(String keyword) throws SQLException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return busDAO.searchBusByPlate(keyword.trim());
    }

}
