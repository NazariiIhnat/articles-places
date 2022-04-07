package entites;

import lombok.Data;
import net.sourceforge.barbecue.Barcode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "container_id")
    private long id;
    private Barcode barcode;
    private String barcodeName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Container parentContainer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<Container> childContainers = new ArrayList<>();

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    public void addCildContainer(Container container) {
        childContainers.add(container);
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public boolean hasChildContainer() {
        return this.childContainers.size() != 0;
    }

    public boolean hasParentContainer() {
        return this.parentContainer != null;
    }
}
