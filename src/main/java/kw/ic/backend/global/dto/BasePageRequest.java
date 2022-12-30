package kw.ic.backend.global.dto;

import java.util.List;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Setter
@AllArgsConstructor
public class BasePageRequest {

    private static final long NEED_CALCULATE = -1L;

    @Min(value = 0L)
    private Integer page;

    @Min(value = 1L)
    private Integer size;

    private Sort sort;

    private Long totalCount;

    public PageRequest getPageRequest() {
        return PageRequest.of(getPage(), getSize(),getSort());
    }

    public Integer getPage() {
        return page == null ? 0 : page;
    }

    public Integer getSize() {
        return size == null ? 10 : size;
    }

    public Sort getSort() {
        return sort == null ? Sort.by(Direction.DESC,"id") : this.sort;
    }

    public Long getTotalCount() {
        return totalCount == null ? NEED_CALCULATE : totalCount;
    }
}
