package BusLogModule.dto;

import java.util.Date;
import java.util.UUID;

public class BusLogDTO {

    private int logId;
    private int busId;
    private String status;
    private String note;
    private UUID createdBy;
    private Date createdat;

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
