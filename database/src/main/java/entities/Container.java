package entities;

import lombok.Data;
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
    private String userReadableInfo;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Container parentContainer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
        return this.childContainers != null;
    }

    public boolean hasParentContainer() {
        return this.parentContainer != null;
    }

    @Override
    public String toString() {
        return getUserReadableInfo();
    }
}
