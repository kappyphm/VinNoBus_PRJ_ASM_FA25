package BusModule.dao;

import BusModule.model.Bus;
import common.db.DBContext;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class BusDAO implements iBusDAO {

    private final DBContext db = new DBContext();

    @Override
    public boolean addBus(Bus bus) throws SQLException {
        Connection cnn = db.getConnection();
        String sql = "INSERT INTO Bus (plate_number, capacity) VALUES (?, ?)";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setString(1, bus.getPlateNumber());
        ps.setInt(2, bus.getCapacity());
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    @Override
    public Bus findBusById(int busId) throws SQLException {
        Bus bus = null;
        Connection cnn = db.getConnection();
        String sql = "SELECT * FROM Bus WHERE bus_id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, busId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bus = new Bus();
            bus.setBusId(rs.getInt("bus_id"));
            bus.setPlateNumber(rs.getString("plate_number"));
            bus.setCapacity(rs.getInt("capacity"));
        }
        rs.close();
        ps.close();
        return bus;
    }

    @Override
    public boolean deleteBus(int busId) throws SQLException {
        Connection cnn = db.getConnection();
        String sql = "DELETE FROM Bus WHERE bus_id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, busId);
        int rows = ps.executeUpdate();
        ps.close();
        cnn.close();
        return rows > 0;
    }

    @Override
    public boolean updateBus(Bus bus) throws SQLException {
        Connection cnn = db.getConnection();
        String sql = "UPDATE Bus SET plate_number = ?, capacity = ? WHERE bus_id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setString(1, bus.getPlateNumber());
        ps.setInt(2, bus.getCapacity());
        ps.setInt(3, bus.getBusId());
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    @Override
    public List<Bus> findAll() throws SQLException {
        List<Bus> list = new ArrayList<>();
        Connection cnn = db.getConnection();
        String sql = "SELECT * FROM Bus";
        Statement st = cnn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Bus bus = new Bus();
            bus.setBusId(rs.getInt("bus_id"));
            bus.setPlateNumber(rs.getString("plate_number"));
            bus.setCapacity(rs.getInt("capacity"));
            list.add(bus);
        }
        rs.close();
        st.close();
        return list;
    }

    @Override
    public List<Bus> findAll(String search, String sort, int page, int pageSize) throws SQLException {
        List<Bus> list = new ArrayList<>();
        Connection cnn = db.getConnection();
        if (search == null) {
            search = "";
        }
        if (sort == null || sort.isEmpty()) {
            sort = "bus_id";
        }
        String sql = "SELECT * FROM Bus WHERE plate_number LIKE ? "
                + "ORDER BY " + sort + " "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setString(1, "%" + search + "%");
        ps.setInt(2, (page - 1) * pageSize);  // ví dụ page=2, pageSize=10 → offset=10
        ps.setInt(3, pageSize);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Bus bus = new Bus();
            bus.setBusId(rs.getInt("bus_id"));
            bus.setPlateNumber(rs.getString("plate_number"));
            bus.setCapacity(rs.getInt("capacity"));
            list.add(bus);
        }
        rs.close();
        ps.close();
        return list;
    }

    @Override
    public int countAll() throws SQLException {
        int count = 0;
        Connection cnn = db.getConnection();
        String sql = "SELECT COUNT(*) FROM Bus";
        Statement st = cnn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        st.close();
        return count;
    }

    @Override
    public boolean assignBusToTrip(int busId, int tripId) throws SQLException {
        String sql = "UPDATE Trip SET bus_id = ? WHERE trip_id = ?";
        try (Connection cnn = db.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, busId);
            ps.setInt(2, tripId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public boolean isPlateNumberExists(String plateNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Bus WHERE plate_number = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plateNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public List<Bus> searchBusByPlate(String keyword) throws SQLException {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM Bus WHERE plate_number LIKE ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%"); // tìm tương đối
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                buses.add(new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity")
                ));
            }
        }
        return buses;
    }

}
