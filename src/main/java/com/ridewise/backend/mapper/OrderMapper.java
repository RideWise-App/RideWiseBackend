package com.ridewise.backend.mapper;

import com.ridewise.backend.dto.OrderDto;
import com.ridewise.backend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userDto(ClientMapper.INSTANCE.mapToDto(order.getClient()))
                .endLocation(LocationMapper.INSTANCE.toDto(order.getEndLocation()))
                .startLocation(LocationMapper.INSTANCE.toDto(order.getStartLocation()))
                .build();
    }

    default List<OrderDto> toDtoList(List<Order> orders) {
        return orders.stream().map(this::toDto).toList();
    }
}
