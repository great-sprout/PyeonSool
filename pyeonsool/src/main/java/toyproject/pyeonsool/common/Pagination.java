package toyproject.pyeonsool.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class Pagination {
    private int blockSize; // 한 블록에 존재할 수 있는 페이지 번호 개수
    private int startPagePerBlock; // 페이지 블록에서의 시작 번호. 1부터 시작
    private int endPagePerBlock; // 페이지 블록에서의 마지막 번호
    private int currentNumber; // 현재 페이지, 0부터 시작
    private int currentPage; // 현재 페이지, 1부터 시작
    private int totalPages; // 전체 페이지 수
    private boolean isFirstBlock;
    private boolean isLastBlock;
    private String sortField; // 정렬 기준 필드
    private String sortDirection; // ASC 혹은 DESC
    private boolean hasNextPage; // 다음 페이지 존재 여부
    private Long totalElements; // 리뷰 총 개수

    public static <T> Pagination of(Page<T> page, int blockSize) {
        int startIndexPerBlock = page.getNumber() / blockSize * blockSize;
        String[] sortInfo = page.getSort().toString().split(": ");

        return new Pagination(blockSize,
                startIndexPerBlock + 1,
                startIndexPerBlock + blockSize,
                page.getNumber(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getNumber() / blockSize == 0,
                page.getNumber() / blockSize == (page.getTotalPages() - 1) / blockSize,
                sortInfo[0],
                sortInfo[1],
                page.hasNext(),
                page.getTotalElements());
    }
}
