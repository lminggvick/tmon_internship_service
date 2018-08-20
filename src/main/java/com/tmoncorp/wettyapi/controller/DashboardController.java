package com.tmoncorp.wettyapi.controller;

import com.tmoncorp.core.controller.BaseApiController;
import com.tmoncorp.wettyapi.model.Dashboard;
import com.tmoncorp.wettyapi.service.DashboardService;
import com.tmoncorp.wettyapi.exception.InvalidParameterException;
import com.tmoncorp.wettyapi.model.Graph;
import com.tmoncorp.wettyapi.service.GraphService;
import com.tmoncorpdev.apihelper.APIDescription;
import com.tmoncorpdev.apihelper.ControllerDescription;
import com.tmoncorpdev.apihelper.ParamDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/dashboard")
@ControllerDescription(title = "대시보드 서비스", description = "대시보드 목록과 데이터를 조회하는 컨트롤러")
public class DashboardController extends BaseApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private GraphService graphService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @APIDescription(title = "대시보드 전체 조회", description = "admin에서 추가한 대시보드 전체 목록 조회")
    public List<Dashboard> getDashboardList() {
        return dashboardService.getDashboardList();
    }

    @RequestMapping(value = "/{dashboardNo}", method = RequestMethod.GET)
    @APIDescription(title = "대시보드 하나 조회", description = "대시보드 리스트 중 하나의 대시보드 정보 조회(그래프 리스트 정보를 포함)")
    public Dashboard getDashboardSummaryInformation(@PathVariable("dashboardNo") int dashboardNo,
                                                    @RequestParam(value = "page", defaultValue = "1", required = false) @ParamDescription(value = "default = 1, 조회할 페이지") int page,
                                                    @RequestParam(value = "max", defaultValue = "10", required = false) int max) {

        if (dashboardNo <= 0) {
            throw new InvalidParameterException("DashboardNo가 잘못 입력되었습니다.");
        }
        return dashboardService.getDashboardSummaryInformation(dashboardNo, page, max);
    }

    @RequestMapping(value = "/{dashboardNo}/graph/{graphId}", method = RequestMethod.GET)
    @APIDescription(title = "한 개의 Graph 데이터 조회", description = "graphId로 Graph 데이터 조회")
    public Graph getGraphData(@PathVariable("dashboardNo") int dashboardNo, @PathVariable("graphId") int graphId) {
        if (dashboardNo <= 0) {
            throw new InvalidParameterException("dashboardNo가 잘못 입력되었습니다.");
        }
        if (graphId <= 0) {
            throw new InvalidParameterException("graphId가 잘못 입력되었습니다.");
        }

        return graphService.getGraphData(dashboardNo, graphId);
    }

}
