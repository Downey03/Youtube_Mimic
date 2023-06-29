package DTO;

import com.googlecode.objectify.annotation.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;

@Data
@Builder
public class SearchDTO {
    private String searchKeyword ;

    public SearchDTO(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public SearchDTO() {
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}
