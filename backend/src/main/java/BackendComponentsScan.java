import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"utils", "location", "item"})
public class BackendComponentsScan {
}
