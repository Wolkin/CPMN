package com.gxs.block.pay;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;
import com.web.util.RsaSignature;

public class PayTest {
	public void testPayCallBack(){
        PayCallBackParam param = new PayCallBackParam();
        param.setAppId("appId");
        param.setOutTradeNo("outTradeNo");
        param.setPaySuccess(true);
        param.setTradeNo("tradeNo");
        param.setSign("�ص�ǩ��");
        //��װǩ����
        StringBuilder builder = new StringBuilder();
        builder.append("appId=").append(param.getAppId())
                .append("&outTradeNo=").append(param.getOutTradeNo())
                .append("&paySuccess=").append(param.isPaySuccess())
                .append("&tradeNo=").append(param.getTradeNo());
        if(RsaSignature.rsaDecrypt(param.getSign(),"˽Կ").equals(builder.toString())){
            //ǩ��ͨ��
        }
    }
	
	public void testPay(String[] args) {
        PayCommonParam param = new PayCommonParam();
        param.setApp_id("");
        param.setMethod("");
        param.setNotify_url("");
        param.setTimestamp(System.currentTimeMillis());
        param.setVersion("1.0.0");
        InitPayParam initPayParam = new InitPayParam();
        initPayParam.setCurrency("GXS");
        initPayParam.setOut_trade_no("�̻�������");
        initPayParam.setSubject("����");
        initPayParam.setTotal_amount(new BigDecimal(10));
        param.setBiz_content(JSON.toJSONString(initPayParam));
        param.setSign(RsaSignature.rsaSign(param.getBiz_content()+param.getTimestamp(), "rsa˽Կ"));
        System.out.println(JSON.toJSONString(param));
    }
}
