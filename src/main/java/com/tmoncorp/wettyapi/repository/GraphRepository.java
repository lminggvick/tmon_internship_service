package com.tmoncorp.wettyapi.repository;

import com.tmoncorp.core.repository.TmonRepositorySupport;
import com.tmoncorp.wettyapi.model.*;
import com.tmoncorp.wettyapi.model.type.BaseTypeCategory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GraphRepository extends TmonRepositorySupport {

    @Autowired
    @Qualifier(value = "internshipSqlSessionFactory")
    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
    }

    public <G extends GraphModel> Graph<G> getGraph(int graphId) {
        return selectOne("getGraph", graphId);
    }

    public List<BaseTypeCategory> getBaseTypeCategories(int graphId) {
        return selectList("getBaseTypeCategories", graphId);
    }

    public int getApiTypeIdByGraphId(int graphId) {
        return selectOne("getApiTypeIdByGraphId", graphId);
    }

    public List<XyGraph> getGraphDataFromXyGraph(int apiTypeId) {
        return selectList("getGraphDataFromXyGraph", apiTypeId);
    }

    public List<CircleGraph> getGraphDataFromCircleGraph(int apiTypeId) {
        return selectList("getGraphDataFromCircleGraph", apiTypeId);
    }

    @Override
    protected String getSqlMapperPrefix() {
        return "com.tmoncorp.wettyapi.graph-sqlmap.";
    }

}
