package weather.wm.com.wmweather.common.bean;

/**
 * Created by HelloKiki on 2017/3/12.
 */

public class Pollutants {

    private String value;

    private String typeEn;

    private String type;

    private boolean isFirst;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTypeEn() {
        return typeEn;
    }

    public void setTypeEn(String typeEn) {
        this.typeEn = typeEn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
