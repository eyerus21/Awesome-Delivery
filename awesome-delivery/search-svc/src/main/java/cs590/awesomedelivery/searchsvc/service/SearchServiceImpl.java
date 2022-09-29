package cs590.awesomedelivery.searchsvc.service;

import cs590.awesomedelivery.searchsvc.domain.Menu;
import cs590.awesomedelivery.searchsvc.domain.Restaurant;
import cs590.awesomedelivery.searchsvc.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    private static final String CACHE = "restaurantCache";

    @Autowired
    private SearchRepository searchRepository;

    public Restaurant save(Restaurant restaurant) {
        log.info("[SAVE] No Cache. Saving on cache.");
        return searchRepository.save(restaurant);
    }

    @Cacheable(value=CACHE, key="#id")
    public Restaurant findById(String id) {
        log.info("[FIND BY ID] No Cache. Saving on cache.");
        return searchRepository.findById(id).orElse(null);
    }

    @Cacheable(value=CACHE, key="'all'")
    public List<Restaurant> findAll() {
        log.info("[FIND ALL] No Cache. Saving on cache.");
        List<Restaurant> restaurants = new ArrayList<>();
        searchRepository.findAll().forEach(restaurants::add);
        return restaurants;
    }

    @Cacheable(value=CACHE, key="'r_'.concat(#name)")
    public List<Restaurant> findByRestaurantName(String name) {
        log.info("[FIND BY R NAME] No Cache. Saving on cache.");
        return searchRepository.findByNameContaining(name);
    }

    @Cacheable(value=CACHE, key="'m_'.concat(#name)")
    public List<Restaurant> findByMenusName(String name) {
        log.info("[FIND BY MENU NAME] No Cache. Saving on cache.");
        return searchRepository.findByMenuNameContaining(name);
    }

    @Cacheable(value=CACHE, key="'rm_'.concat(#restaurantName).concat(#menuName)")
    public List<Menu> findByNameAndMenusName(String restaurantName, String menuName) {
        log.info("[FIND BY MENU NAME] No Cache. Saving on cache.");
        Restaurant restaurant = searchRepository.findByNameAndMenuName(restaurantName, menuName);
        if (restaurant != null && !restaurant.getMenu().isEmpty()) {
            return restaurant.getMenu().stream().filter(menu -> menu.getName().equals(menuName)).collect(Collectors.toList());
        }
        else {
            return List.of();
        }
    }

    public void deleteById(String id) {
        searchRepository.deleteById(id);
    }

    @CacheEvict(value=CACHE, key="#id")
    public void removeCacheById(String id) {
        log.info("[DELETE] Evicting...");
    }

    @CacheEvict(value=CACHE, key="'r_'.concat(#name)")
    public void removeCacheByRestaurantName(String name) {
        log.info("[REMOVE BY RESTAURANT NAME] Evicting...");
    }

    @CacheEvict(value=CACHE, key="'m_'.concat(#name)")
    public void removeCacheByMenuName(String name) {
        log.info("[REMOVE BY MENU NAME] Evicting...");
    }

    @CacheEvict(value=CACHE, key="'rm_'.concat(#restaurantName).concat(#menuName)")
    public void removeCacheByNameAndMenuName(String restaurantName, String menuName) {
        log.info("[REMOVE BY RESTAURANT AND MENU NAME] Evicting...");
    }

    @CacheEvict(value=CACHE, key="'all'")
    public void removeCacheForGetAll() {
        log.info("[REMOVE BY FIND ALL] Evicting...");
    }

}
