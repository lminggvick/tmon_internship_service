package com.tmoncorp.wettyapi.service.factory;

import com.tmoncorp.wettyapi.model.GraphModel;
import com.tmoncorp.wettyapi.model.type.GraphTypeGroup;

import java.util.List;

/**
 * switch case 없애고 타입 체크를 구현 클래스에서 할 수 있도록
 *  바꿨을 때 장점 -> 수정이 일어나도 Factory 클래스에 case를 추가하지 안
 */
public interface GraphModelCreator<G extends GraphModel> {
    List<G> createGraphDataList(int apiTypeId);
    boolean isGraphType(GraphTypeGroup graphTypeGroup);
}
