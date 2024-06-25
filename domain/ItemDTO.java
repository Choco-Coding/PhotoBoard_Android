package domain;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO { //item Data Transfer Object
    private String id;
    private String title;
    private String contents;
    private String imageTitle;

}