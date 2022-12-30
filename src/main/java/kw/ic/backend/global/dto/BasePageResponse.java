package kw.ic.backend.global.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
public class BasePageResponse<EN> {

    private int totalPage;

    private long totalCount;

    private int curPage;

    private int pageSize;

    private int startIndex;
    private int endIndex;

    private boolean prev;
    private boolean next;

    private List<Integer> pageList;

    public BasePageResponse(Page<EN> result) {
        this.totalPage = result.getTotalPages();
        this.totalCount = result.getTotalElements();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.curPage=pageable.getPageNumber()+1;
        this.pageSize = pageable.getPageSize();

        int tempEnd=(int)(Math.ceil(curPage/10.0))*10;

        startIndex= tempEnd-9;

        prev=startIndex>1;

        endIndex=totalPage>tempEnd ? tempEnd : totalPage;

        next=totalPage>tempEnd;

        pageList= IntStream.rangeClosed(startIndex,endIndex).boxed().collect(Collectors.toList());
    }
}
