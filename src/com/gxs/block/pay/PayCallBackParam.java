package com.gxs.block.pay;

public class PayCallBackParam {
	private String appId;
    private String tradeNo;
    private String outTradeNo;
    private boolean paySuccess;
    private String callbackParams;
    /**
     * —È«©,RSA(appId=&outTradeNo=&paySuccess=&tradeNo=)
     */
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public boolean isPaySuccess() {
        return paySuccess;
    }

    public void setPaySuccess(boolean paySuccess) {
        this.paySuccess = paySuccess;
    }

    public String getCallbackParams() {
        return callbackParams;
    }

    public void setCallbackParams(String callbackParams) {
        this.callbackParams = callbackParams;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
