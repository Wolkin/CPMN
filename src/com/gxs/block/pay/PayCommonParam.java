package com.gxs.block.pay;

public class PayCommonParam {
	private String app_id;
    /**
     * ���÷���
     */
    private String method;
    /**
     * ����ʱ�������������ʱ��Ƚ���5������
     */
    private Long timestamp;
    /**
     * ���õĽӿڰ汾���̶�Ϊ��1.0
     */
    private String version;
    /**
     * �ص���ַ
     */
    private String notify_url;
    /**
     * ������
     */
    private String biz_content;
    private String sign;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
