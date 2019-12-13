package com.siv.reqresexample;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageResponse {
    @SerializedName("data")
    private List<ReqresModel> data;
    @SerializedName("page")
    private Long page;
    @SerializedName("per_page")
    private Long perPage;
    @SerializedName("total")
    private Long total;
    @SerializedName("total_pages")
    private Long totalPages;

    public List<ReqresModel> getData() {
        return data;
    }

    public void setData(List<ReqresModel> data) {
        this.data = data;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPerPage() {
        return perPage;
    }

    public void setPerPage(Long perPage) {
        this.perPage = perPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
