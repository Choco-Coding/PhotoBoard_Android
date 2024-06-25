package domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Data
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
public class Item { //item Class
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto Increase
    private Long id;                                    // ID (primary key)
    @Column(length = 20)                // Title (constraint length 20)
    private String title;
    @Column(length = 100) // Contents (constraint length 100)
    private String contents;
    @Column(nullable = false)
    private String imageTitle;       // image file name


}