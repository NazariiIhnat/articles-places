package entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CODE_VALUE")
    private Code code;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    private int quantity;

    public String getCode() {
        return code.getCode();
    }
}

