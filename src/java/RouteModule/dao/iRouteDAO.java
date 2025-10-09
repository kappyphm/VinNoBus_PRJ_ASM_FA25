package RouteModule.dao;

import RouteModule.model.Route;
import java.util.List;
import java.sql.*;

public interface iRouteDAO {

    // Thêm tuyến mới
    public boolean addRoute(Route route) throws SQLException;

    // Cập nhật thông tin tuyến (sửa)
    public boolean updateRoute(Route route) throws SQLException;

    // Lấy thông tin chi tiết tuyến theo ID
    public Route getRouteById(int routeId) throws SQLException;

    // Lấy danh sách tất cả các tuyến
    public List<Route> getAllRoutes() throws SQLException;

}
