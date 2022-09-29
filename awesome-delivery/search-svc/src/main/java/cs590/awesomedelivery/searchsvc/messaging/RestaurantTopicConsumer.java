//package cs590.awesomedelivery.searchsvc.messaging;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import cs590.awesomedelivery.searchsvc.domain.Restaurant;
//import cs590.awesomedelivery.searchsvc.service.SearchService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class RestaurantTopicConsumer {
//    @Autowired
//    private SearchService searchService;
//
//    @KafkaListener(topics ="#{'${kafka.topic.delete}'}", groupId = "#{'${spring.kafka.consumer.group-id}'}")
//    public void consumeDelete(String id){
//        Restaurant restaurant = searchService.findById(id);
//        searchService.deleteById(id);
//        invalidateCache(restaurant);
//        log.info("Restaurant ["+ id +"] is deleted");
//    }
//
//    @KafkaListener(topics ="#{'${kafka.topic.save}'}", groupId = "#{'${spring.kafka.consumer.group-id}'}")
//    public void consumeSave(String message){
//        ObjectMapper objectMapper = new ObjectMapper();
//        Restaurant restaurant = null;
//        try {
//            restaurant = objectMapper.readValue(message, Restaurant.class);
//        } catch (JsonProcessingException e) {
//            log.error(e.getLocalizedMessage());
//        }
//        searchService.save(restaurant);
//        invalidateCache(restaurant);
//        log.info("Restaurant ["+restaurant.getId()+"] is saved");
//    }
//
//    public void invalidateCache(Restaurant restaurant) {
//        searchService.removeCacheForGetAll();
//        searchService.removeCacheById(restaurant.getId());
//        searchService.removeCacheByRestaurantName(restaurant.getName());
//        restaurant.getMenus().forEach(m -> searchService.removeCacheByNameAndMenuName(restaurant.getName(), m.getName()));
//        restaurant.getMenus().forEach(m -> searchService.removeCacheByMenuName(m.getName()));
//    }
//}