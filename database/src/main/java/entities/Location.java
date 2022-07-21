package entities;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private long id;
    private String userReadableInfo;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parentLocation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<Location> childLocations = new ArrayList<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public void addCildLocation(Location location) {
        childLocations.add(location);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public boolean hasChildLocations() {
        return this.childLocations.size() > 0;
    }

    public boolean hasLocation(String name) {
        if(!hasChildLocations()) return false;
        else return childLocations.stream().filter(x -> x.getUserReadableInfo().equals(name)).count() == 1;
    }

    public boolean hasParentLocation() {
        return this.parentLocation != null;
    }

    public boolean hasItem(String code) {
        if(!hasItems()) return false;
        else return items.stream().filter(x -> x.getCode().equals(code)).count() == 1;
    }

    public boolean hasItems() {return items.size() > 0;}

    @Override
    public String toString() {
        return getUserReadableInfo();
    }
}
