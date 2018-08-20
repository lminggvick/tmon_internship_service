package com.tmoncorp.wettyapi.repository;

import com.tmoncorp.core.repository.TmonRepositorySupport;
import com.tmoncorp.wettyapi.model.Dashboard;
import com.tmoncorp.wettyapi.model.DashboardGraphCollection;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardRepository extends TmonRepositorySupport {

    @Autowired
    @Qualifier(value = "internshipSqlSessionFactory")
    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
    }

    public List<Dashboard> getDashboardList() {
        return selectList("getDashboardList");
    }

    public List<DashboardGraphCollection> getDashboardGraphCollections() {
        return selectList("getDashboardGraphCollections");
    }

    public Dashboard getDashboardSummaryInformation(int dashboardNo) {
        return selectOne("getDashboardSummaryInformation", dashboardNo);
    }

    public DashboardGraphCollection getDashboardGraph(DashboardGraphCollection graph) {
        return selectOne("getDashboardGraph", graph);
    }

    @Override
    protected String getSqlMapperPrefix() {
        return "com.tmoncorp.wettyapi.dashboard-sqlmap.";
    }
}
