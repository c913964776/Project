package com.atguigu.yygh.hosp.receiver;

import com.atguigu.common.rabbit.constant.MqConst;
import com.atguigu.common.rabbit.service.RabbitService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.msm.MsmVo;
import com.atguigu.yygh.vo.order.OrderMqVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HospitalReceiver {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RabbitService rabbitService;

//    @RabbitListener注解指定目标方法来作为消费消息的方法，通过注解参数指定所监听的队列或者Binding。
//    使用@RabbitListener可以设置一个自己明确默认值的RabbitListenerContainerFactory对象。
    //具体配置都在MQconst里面

    //订阅的内容  如果有内容传递过来的话就进行方法的调用

    ///exchange是怎么知道消息应该推到哪个queue呢，
    // 这就要通过绑定queue与exchange时的routingkey了，通过代码进行绑定并且指定routingkey

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_ORDER),
            key = {MqConst.ROUTING_ORDER}
    ))

    public void receiver(OrderMqVo orderMqVo, Message message, Channel channel) throws IOException {
        if(null != orderMqVo.getAvailableNumber()) {
            //下单成功更新预约数
            Schedule schedule = scheduleService.getScheduleId(orderMqVo.getScheduleId());
            schedule.setReservedNumber(orderMqVo.getReservedNumber());
            schedule.setAvailableNumber(orderMqVo.getAvailableNumber());
            scheduleService.update(schedule);
        } else {
            //取消预约更新预约数
            Schedule schedule = scheduleService.getScheduleId(orderMqVo.getScheduleId());
            int availableNumber = schedule.getAvailableNumber().intValue() + 1;
            schedule.setAvailableNumber(availableNumber);
            scheduleService.update(schedule);
        }

        //发送短信
        MsmVo msmVo = orderMqVo.getMsmVo();
        if(null != msmVo) {
            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo);
        }
    }

}