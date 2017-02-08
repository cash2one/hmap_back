package hmap.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 数据返回单个对象.
 *
 * @author njq.niu@hand-china.com
 */
public class ResponseFormData {

    // 返回状态编码
    @JsonInclude(Include.NON_NULL)
    private String code;

    // 返回信息
    @JsonInclude(Include.NON_NULL)
    private String message;

    //数据
    @JsonInclude(Include.NON_NULL)
    private Object returnData;

    //状态
    @JsonInclude(Include.NON_NULL)
    private String status = "S";

    //总数
    @JsonInclude(Include.NON_NULL)
    private Long total;

    public ResponseFormData() {
    }

    public ResponseFormData(Boolean status) {
    	if(status){
    		setStatus("S");
    	}else{
    		setStatus("N");
    	}
    }
    public ResponseFormData(Object obj) {
        this(true);
        setReturnData(obj);
        setTotal(1L);
        
    }
	public ResponseFormData(Object obj, Boolean status) {
		if(status){
			setStatus("S");
		}else{
			setStatus("N");
		}
		setReturnData(obj);
		setTotal(1L);

	}
    public ResponseFormData(List<?> list) {
        this(true);
        setReturnData(list);
        if (list instanceof Page) {
            setTotal(((Page<?>) list).getTotal());
        } else {
            setTotal((long) list.size());
        }
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ResponseFormData{" +
				"code='" + code + '\'' +
				", message='" + message + '\'' +
				", returnData=" + returnData +
				", status='" + status + '\'' +
				", total=" + total +
				'}';
	}
}
