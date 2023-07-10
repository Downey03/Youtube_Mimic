package Entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlayListItemMap {
    @Id
    String playListItemMapId;
    @Index
    String playListId;
    @Index
    String videoTitle;
    @Index
    String videoLink;
    @Index
    String videoThumbnail;

}
