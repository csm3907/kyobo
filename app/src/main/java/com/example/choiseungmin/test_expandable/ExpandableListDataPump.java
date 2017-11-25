package com.example.choiseungmin.test_expandable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> basketball = new ArrayList<String>();
        basketball.add("계약조회");
        basketball.add("보험료납입");
        basketball.add("사고보험금");
        basketball.add("변액보험조회");
        basketball.add("거래내역조회");
        basketball.add("자동이체");
        basketball.add("카카오톡 알림톡 사용");

        List<String> cricket = new ArrayList<String>();
        cricket.add("조회상환");
        cricket.add("신청");
        cricket.add("조회상환");
        cricket.add("신청");

        List<String> football = new ArrayList<String>();
        football.add("펀드계약조회");
        football.add("펀드매수");
        football.add("펀드환매");
        football.add("신탁계약조회");
        football.add("신탁입금");
        football.add("신탁출금");


        List<String> temp = new ArrayList<String>();
        temp.add("공인인증서");

        expandableListDetail.put("보험", basketball);
        expandableListDetail.put("펀드/신탁", cricket);
        expandableListDetail.put("인증/고객센터",temp);
        expandableListDetail.put("대출", football);


        return expandableListDetail;
    }
}