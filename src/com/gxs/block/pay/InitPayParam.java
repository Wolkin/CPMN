package com.gxs.block.pay;

import java.math.BigDecimal;

public class InitPayParam {
	/**
     * 商品的标题
     */
    private String subject;
    /**
     *  商户唯一订单号
     */
    private String out_trade_no;
    /**
     * 该笔订单允许的最晚付款时间，逾期将关闭交易，默认30m。取值范围：1m～1d，m-分钟，h-小时，d-天
     */
    private String pay_expire;
    /**
     * 订单总金额，单位为元，精确到小数点后五位，取值范围[0.00001,100000000]
     */
    private BigDecimal total_amount;
    /**
     * 收款方的用户uuid,可不填
     */
    private String seller_id;
    /**
     * 支付币种，GXS
     */
    private String currency;
    /**
     * 支付回调时传的参数
     */
    private String callback_params;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPay_expire() {
        return pay_expire;
    }

    public void setPay_expire(String pay_expire) {
        this.pay_expire = pay_expire;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCallback_params() {
        return callback_params;
    }

    public void setCallback_params(String callback_params) {
        this.callback_params = callback_params;
    }
}
