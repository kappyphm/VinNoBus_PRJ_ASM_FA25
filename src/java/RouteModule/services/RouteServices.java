package RouteModule.services;

import RouteModule.dao.RouteDAO;
import RouteModule.model.Route;
import java.sql.*;
import java.util.*;

public class RouteServices {

    private final RouteDAO routeDAO;

    public RouteServices() {
        this.routeDAO = new RouteDAO();
    }

    // Thêm tuyến đường mới
    public boolean addRoute(Route route) throws SQLException {
        return routeDAO.addRoute(route);
    }

    // Cập nhật tuyến đường
    public boolean updateRoute(Route route) throws SQLException {
        return routeDAO.updateRoute(route);
    }

    // Lấy thông tin Route theo ID
    public Route getRouteById(int routeId) throws SQLException {
        return routeDAO.getRouteById(routeId);
    }

    // Lấy danh sách tất cả các Route
    public List<Route> getAllRoutes() throws SQLException {
        return routeDAO.getAllRoutes();
    }
}
