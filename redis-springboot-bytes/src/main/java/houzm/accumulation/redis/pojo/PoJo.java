package houzm.accumulation.redis.pojo;

/**
 * Author: hzm_dream@163.com
 * Date:  2018/11/21 17:33
 * Modified By:
 * Description：
 */
public class PoJo {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "PoJo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
