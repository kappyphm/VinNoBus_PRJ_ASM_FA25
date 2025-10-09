package RouteModule.dto;

public class RouteDTO {

    private int routeId;
    private String routeName;
    private String type;
    private int frequency;

    public int getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getType() {
        return type;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
