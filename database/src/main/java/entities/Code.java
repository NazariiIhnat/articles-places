package entities;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Code {

    @Id
    @Column(name = "CODE_VALUE", nullable = false)
    private String code;
    public Code(String code) {
        this.code = code;
    }
}
