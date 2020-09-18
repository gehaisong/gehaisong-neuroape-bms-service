package com.utechworld.neuroape.common.result;


import java.io.Serializable;

/**
 * controller层json视图数据格式封装.
 */
public class ResultPage<V> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private V data;
    private Integer pageNo;
    private Integer pageSize;
    /**总条数*/
    private Long total;
    /**总页数*/
    private Integer pages;
    private String message;

    public static <V> ResultPage<V> ok(V data ,Integer pageNo, Integer pageSize, Long total) {
        return new ResultPage( data , pageNo,  pageSize,  total);
    }

    public static <V> ResultPage<V> fail() {
        return new ResultPage(CodeEnum.FAILED.getValue());
    }

    private ResultPage(){
    }

    private ResultPage(Integer code){
        this.code=code;
    }
    private ResultPage(V data ,Integer pageNo, Integer pageSize, Long total) {
        this.code = CodeEnum.SUCCESS.getValue();
        this.data = data;
        this.pages=0;
        if(total!=0&&pageSize!=0){
            Long pages=total/pageSize;
            if(total%pageSize!=0){
                pages++;
            }
            this.pages=pages.intValue();
        }
        this.total=total;
        this.pageSize=pageSize;
        pageNo=pageNo>pages?pages:pageNo;
        pageNo=pageNo<1?1:pageNo;
        this.pageNo=pageNo;
        this.message = "";
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public V getData() {
        return data;
    }

    public ResultPage<V> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ResultPage<V> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResultPage<V> setData(V obj) {
        this.data = obj;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}