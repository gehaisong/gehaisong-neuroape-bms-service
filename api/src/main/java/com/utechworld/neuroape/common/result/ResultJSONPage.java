package com.utechworld.neuroape.common.result;


import java.io.Serializable;

/**
 * controller层json视图数据格式封装.
 * <p>
 * 示例:<br>
 *
 * <pre>
 *
 * Controller方法:
 * &#064;RequestMapping(value = &quot;/test&quot;)
 * &#064;ResponseBody
 * public ResultData&lt;User&gt; testMethod(@RequestParam(&quot;value&quot;) String value) {
 *     // 业务处理....
 *     User user = new User();
 *     user.setId(1);
 *     user.setName(&quot;tom&quot;);
 *     return new ResultData&lt;&gt;(0, &quot;操作成功&quot;, user);
 * }
 * 返回JSON数据:
 * {
 *     "code": 0,
 *     "message": "操作成功",
 *     "data": {
 *         "id": 1,
 *         "name": "tom"
 *     }
 * }
 *
 */
public class ResultJSONPage<V> implements Serializable {

    private static final long serialVersionUID = 1L;
    /** The code. */
    private Integer code;

    /** The message. */
    private String message;
    /** The obj. */
    private V data;
    /**分页查询总数*/
    private long totalRows;
    /**总页数*/
    private long totalPage;
    private int pageSize;
    private long pageNo;

    /**
     * Instantiates a new result data.
     */
    public ResultJSONPage() {
        this(0, "");
    }

    /**
     * Instantiates a new result data.
     *
     * @param code the code
     * @param message the message
     */
    public ResultJSONPage(Integer code, String message) {
        this(code, message, null);
    }

    /**
     * Instantiates a new result data.
     *
     * @param code the code
     * @param obj the obj
     */
    public ResultJSONPage(Integer code, V obj) {
        this(code, "", obj);
    }

    /**
     * Instantiates a new result data.
     *
     * @param code the code
     * @param message the message
     * @param obj the obj
     */
    public ResultJSONPage(Integer code, String message, V obj) {
        this.code = code;
        this.message = message;
        this.data = obj;
    }
    public ResultJSONPage( V obj, long totalRows,  long pageNo,int pageSize) {
        this.code = ErrorCode.SUCCESS.getValue();
        this.data = obj;
        this.totalPage=0;
        if(totalRows!=0&&pageSize!=0){
            Long pages=totalRows/pageSize;
            if(totalRows%pageSize!=0){
                pages++;
            }
            this.totalPage=pages;
        }
        this.totalRows=totalRows;
        this.pageSize=pageSize;
        pageNo=pageNo>totalPage?totalPage:pageNo;
        pageNo=pageNo<1?1:pageNo;
        this.pageNo=pageNo;
        this.message = "";
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the obj.
     *
     * @return the obj
     */
    public V getData() {
        return data;
    }

    /**
     * Sets the code.
     *
     * @param code the code
     * @return the result data
     */
    public ResultJSONPage<V> setCode(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * Sets the message.
     *
     * @param message the message
     * @return the result data
     */
    public ResultJSONPage<V> setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Sets the obj.
     *
     * @param obj the obj
     * @return the result data
     */
    public ResultJSONPage<V> setData(V obj) {
        this.data = obj;
        return this;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
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

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
}