package com.tmoncorp.wettyapi.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmoncorp.wettyapi.exception.GraphCreationException;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GraphTypeGroup implements EnumMapperGroupType<GraphSubType> {
    XY_GRAPH("XY 그래프", Arrays.asList(GraphSubType.BAR_GRAPH, GraphSubType.LINEAR_GRAPH), GraphSubType.UNKNOWN),
    CIRCLE_GRAPH("CIRCLE 그래프", Arrays.asList(GraphSubType.PIE_GRAPH), GraphSubType.UNKNOWN);

    private String title;
    private List<GraphSubType> categories;
    private GraphSubType graphSubType;

    GraphTypeGroup(String title) {
        this.title = title;
    }

    GraphTypeGroup(String title, List<GraphSubType> categories) {
        this.title = title;
        this.categories = categories;
    }

    GraphTypeGroup(String title, List<GraphSubType> categories, GraphSubType graphSubType) {
        this.title = title;
        this.categories = categories;
        this.graphSubType = graphSubType;
    }

    /**
     *
     * @param inputGraphSubType SubType을 input으로 받으면, 속해있는 Group을 찾고 SubType과 함께 리턴해준다.
     * @return
     */
    @JsonCreator
    public static GraphTypeGroup create(String inputGraphSubType) {
        for (GraphTypeGroup group : GraphTypeGroup.values()) {
            if (group.getCategories().contains(GraphSubType.valueOf(inputGraphSubType))) {
                group.graphSubType = GraphSubType.valueOf(inputGraphSubType);
                return group;
            }
        }
        throw new GraphCreationException();
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public List<GraphSubType> getCategories() {
        return categories;
    }

    public GraphSubType getGraphSubType() {
        return graphSubType;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + name() + '\'' +
                ", title='" + title + '\'' +
                ", categories=" + categories + '\'' +
                ", graphSubType=" + graphSubType +
                '}';
    }
}
