package com.levelup.jpaadvancedpersistencelab.inheritence;

import com.levelup.jpaadvancedpersistencelab.entity.OrderItem;
import com.levelup.jpaadvancedpersistencelab.entity.OrderItemId;
import com.levelup.jpaadvancedpersistencelab.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CompositeKeyTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @Transactional
    void shouldPersistAndFindEntityUsingEmbeddedId() {

        OrderItemId id = new OrderItemId(100L, 200L);

        OrderItem orderItem = new OrderItem(id, 2, new BigDecimal("499.99"));

        orderItemRepository.saveAndFlush(orderItem);

        OrderItem saved = orderItemRepository.findById(id).orElseThrow();

        assertThat(saved.getId().getOrderId())
                .isEqualTo(100L);

        assertThat(saved.getId().getProductId())
                .isEqualTo(200L);

        assertThat(saved.getQuantity())
                .isEqualTo(2);

        assertThat(saved.getPrice())
                .isEqualByComparingTo("499.99");
    }
}
