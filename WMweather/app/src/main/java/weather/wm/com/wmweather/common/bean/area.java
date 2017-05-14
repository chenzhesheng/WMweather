package weather.wm.com.wmweather.common.bean;

/**
 * Created by HelloKiki on 2017/4/2.
 */

public class area {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "area{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
