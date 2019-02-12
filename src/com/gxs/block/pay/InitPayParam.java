package com.gxs.block.pay;

import java.math.BigDecimal;

public class InitPayParam {
	/**
     * ��Ʒ�ı���
     */
    private String subject;
    /**
     *  �̻�Ψһ������
     */
    private String out_trade_no;
    /**
     * �ñʶ��������������ʱ�䣬���ڽ��رս��ף�Ĭ��30m��ȡֵ��Χ��1m��1d��m-���ӣ�h-Сʱ��d-��
     */
    private String pay_expire;
    /**
     * �����ܽ���λΪԪ����ȷ��С�������λ��ȡֵ��Χ[0.00001,100000000]
     */
    private BigDecimal total_amount;
    /**
     * �տ���û�uuid,�ɲ���
     */
    private String seller_id;
    /**
     * ֧�����֣�GXS
     */
    private String currency;
    /**
     * ֧���ص�ʱ���Ĳ���
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
