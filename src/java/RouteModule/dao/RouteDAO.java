package RouteModule.dao;

import RouteModule.model.Route;
import common.db.DBContext;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RouteDAO implements iRouteDAO {

    private final DBContext db = new DBContext();

    @Override
    public boolean addRoute(Route route) throws SQLException {
        // Kiểm tra type hợp lệ
        List<String> validTypes = Arrays.asList("ROUND_TRIP", "CIRCULAR");
        if (!validTypes.contains(route.getType())) {
            throw new SQLException("Type không hợp lệ: " + route.getType());
        }
        // Kiểm tra frequency >= 0
        if (route.getFrequency() < 0) {
            throw new SQLException("Frequency phải >= 0: " + route.getFrequency());
        }
        String sql = "INSERT INTO Route (route_name, type, frequency) VALUES (?, ?, ?)";
        try (Connection cnn = db.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setString(1, route.getRouteName());
            ps.setString(2, route.getType());
            ps.setInt(3, route.getFrequency());
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public boolean updateRoute(Route route) throws SQLException {
        String sql = "UPDATE Route SET route_name = ?, type = ?, frequency = ? WHERE route_id = ?";
        try (Connection cnn = db.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setString(1, route.getRouteName());
            ps.setString(2, route.getType());
            ps.setInt(3, route.getFrequency());
            ps.setInt(4, route.getRouteId());
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public Route getRouteById(int routeId) throws SQLException {
        String sql = "SELECT * FROM Route WHERE route_id = ?";
        try (Connection cnn = db.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Route(
                            rs.getInt("route_id"),
                            rs.getString("route_name"),
                            rs.getString("type"),
                            rs.getInt("frequency")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Route> getAllRoutes() throws SQLException {
        List<Route> list = new ArrayList<>();
        String sql = "SELECT * FROM Route";
        try (Connection cnn = db.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Route route = new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        rs.getString("type"),
                        rs.getInt("frequency")
                );
                list.add(route);
            }
        }
        return list;
    }

}
