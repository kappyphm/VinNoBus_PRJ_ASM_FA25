package BusLogModule.model;

import java.util.Date;
import java.util.UUID;

public class BusLog {

    private int logId;
    private int busId;
    private String status;
    private String note;
    private UUID createdBy;
    private Date createdat;

    public BusLog() {
    }

    public BusLog(int logId, int busId, String status, String note, UUID createdBy, Date createdat) {
        this.logId = logId;
        this.busId = busId;
        this.status = status;
        this.note = note;
        this.createdBy = createdBy;
        this.createdat = createdat;
    }

    public int getLogId() {
        return logId;
    }

    public int getBusId() {
        return busId;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

}
