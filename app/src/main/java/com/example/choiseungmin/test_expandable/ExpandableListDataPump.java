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
        basketball.add("공유하기");

        List<String> cricket = new ArrayList<String>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("England");
        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> temp = new ArrayList<String>();
        temp.add("공인인증서");

        expandableListDetail.put("보험", basketball);
        expandableListDetail.put("펀드/신탁", cricket);
        expandableListDetail.put("인증/고객센터",temp);
        expandableListDetail.put("대출", football);


        return expandableListDetail;
    }
}