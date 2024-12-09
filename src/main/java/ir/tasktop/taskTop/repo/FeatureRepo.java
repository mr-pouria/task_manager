package ir.tasktop.taskTop.repo;


import ir.tasktop.taskTop.model.Feature;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepo extends JpaRepository<Feature, Long> {
    boolean findAllByName(String name);

    boolean existsByName(String name);
}
