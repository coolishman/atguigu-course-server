package cn.edu.whut.springbear.course.service.order.service;

import cn.edu.whut.springbear.course.common.model.pojo.order.OrderInfo;
import cn.edu.whut.springbear.course.common.model.vo.order.OrderFormVo;
import cn.edu.whut.springbear.course.common.model.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author Spring-_-Bear
 * @since 2022-10-24
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 查询订单分页数据
     *
     * @param curNum           当前页码
     * @param pageSize         每页显示的数量
     * @param orderInfoQueryVo 额外查询条件
     * @return 订单分页数据
     */
    Page<OrderInfo> getOrderPageData(Integer curNum, Integer pageSize, OrderInfoQueryVo orderInfoQueryVo);

    /**
     * 创建点播课程订单并保存
     *
     * @param orderFormVo 订单表单 VO，包含课程 ID、优惠券 ID、优惠券领取表 ID
     * @return 订单创建成功：返回生成的订单 ID
     */
    Long createOrder(OrderFormVo orderFormVo);
}
