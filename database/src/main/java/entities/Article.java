package entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "container_id")
    private Container container;
}

