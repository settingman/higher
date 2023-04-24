package com.hyundai.higher.mapper.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hyundai.higher.domain.cart.OrderItem;
import com.hyundai.higher.domain.order.OrderSheet;
import com.hyundai.higher.service.order.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class OrderMapperTest {
    
    @Mock
    private OrderMapper orderMapper;
    
    @InjectMocks
    private OrderService orderService;
    
    private OrderSheet orderSheet;
    private OrderItem orderItem;
    private String mId;
    private String oId;
    private String pCode;
    private int pAmount;
    private String itemNm;
    private String ordStrtDt;
    private String ordEndDt;
    
    @Before
    public void setUp() {
        mId = "member1";
        oId = "order1";
        pCode = "product1";
        pAmount = 2;
        itemNm = "product1";
        ordStrtDt = "2022-01-01";
        ordEndDt = "2022-01-01";
        orderSheet = new OrderSheet();
        orderSheet.setOrderId(oId);
        orderItem = new OrderItem();
        orderItem.setPcode(pCode);
    }
    
    @Test
    public void testInsertOrder() {
        orderMapper.insertOrder(orderSheet, mId);
        verify(orderMapper).insertOrder(orderSheet, mId);
    }
    
    @Test
    public void testInsertOrderList() {
        orderMapper.insertOrderList(oId, pCode, pAmount, mId);
        verify(orderMapper).insertOrderList(oId, pCode, pAmount, mId);
    }
    
    @Test
    public void testSelectCartItem() {
        when(orderMapper.selectCartItem(mId, pCode)).thenReturn(orderItem);
        OrderItem result = orderMapper.selectCartItem(mId, pCode);
        assertEquals(orderItem, result);
    }
    
    @Test
    public void testFindOrderList() {
        List<OrderSheet> orderList = new ArrayList<>();
        orderList.add(orderSheet);
        when(orderMapper.findOrderList(mId, ordStrtDt, ordEndDt, itemNm)).thenReturn(orderList);
        List<OrderSheet> result = orderMapper.findOrderList(mId, ordStrtDt, ordEndDt, itemNm);
        assertEquals(orderList, result);
    }
}
