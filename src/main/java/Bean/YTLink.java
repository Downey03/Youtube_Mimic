package Bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class YTLink {
    @Id
    String videoId;
    @Index
    String videoTitle;
    @Index
    String videoLink;
    @Index
    String videoThumbnail;
}
