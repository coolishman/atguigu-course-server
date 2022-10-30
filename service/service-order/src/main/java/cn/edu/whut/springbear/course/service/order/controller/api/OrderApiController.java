package cn.edu.whut.springbear.course.service.order.controller.api;

import cn.edu.whut.springbear.course.common.model.vo.order.OrderFormVo;
import cn.edu.whut.springbear.course.common.util.Result;
import cn.edu.whut.springbear.course.service.order.service.OrderInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @datetime 2022-10-30 09:59
 */
@RestController
@RequestMapping("api/order")
public class OrderApiController {
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation("新增点播课程订单")
    @PostMapping("save")
    public Result submitOrder(@RequestBody OrderFormVo orderFormVo) {
        // 生成订单，返回生成的订单 ID
        Long orderId = orderInfoService.createOrder(orderFormVo);
        return orderId == null ? Result.fail("新增点播课程订单失败", null) : Result.success("新增点播课程订单成功", orderId);
    }
}
