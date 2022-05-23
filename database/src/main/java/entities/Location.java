package entities;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "container_id")
    private long id;
    private String userReadableInfo;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parentLocation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<Location> childLocations = new ArrayList<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    public void addCildLocation(Location location) {
        childLocations.add(location);
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public boolean hasChildLocation() {
        return this.childLocations != null;
    }

    public boolean hasParentLocation() {
        return this.parentLocation != null;
    }

    @Override
    public String toString() {
        return getUserReadableInfo();
    }
}
