package BusLogModule.dao;

import BusLogModule.model.BusLog;
import java.util.List;
import java.util.UUID;

public interface iBusLogDAO {

    // Thêm một bản ghi BusLog mới
    boolean addBusLog(BusLog busLog);

    // Cập nhật thông tin của BusLog theo logId
    boolean updateBusLog(BusLog busLog);

    // Xóa BusLog theo logId
    boolean deleteBusLog(int logId);

    // Lấy một BusLog theo logId
    BusLog getBusLogById(int logId);

    // Lấy danh sách tất cả BusLog
    List<BusLog> getAllBusLogs();

    // Lấy danh sách BusLog theo busId
    List<BusLog> getBusLogsByBusId(int busId);

    // Lấy danh sách BusLog theo createdBy (UUID của user)
    List<BusLog> getBusLogsByUser(UUID createdBy);
}
