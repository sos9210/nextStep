package next.model;

public class Paging {
    private int startPage;      //시작페이지
    private int endPage;        //마지막페이지
    private int currentPage;    //현재페이지
    private int totalPost;      //모든글갯수
    private int pageNum = 5;   //한번에 표시되는 페이지 갯수
    private int pageSize = 5;  //한페이지당 글갯수
    private int totalPage;      //모든 페이지 갯수

    @Override
    public String toString() {
        return "Paging{" +
                "startPage=" + startPage +
                ", endPage=" + endPage +
                ", currentPage=" + currentPage +
                ", totalPost=" + totalPost +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    public Paging(int startPage, int endPage, int currentPage) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.currentPage = currentPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(int totalPost) {
        this.totalPost = totalPost;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
