package no.shoppifly;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class ShoppingCartController {

    @Autowired
    private final CartService cartService;

    @Autowired
    private MeterRegistry meterRegistry;

    public ShoppingCartController(CartService cartService, MeterRegistry meterRegistry) {
        this.cartService = cartService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping(path = "/cart/{id}")
    public Cart getCart(@PathVariable String id) {
        return cartService.getCart(id);
    }

    /**
     * Checks out a shopping cart. Removes the cart, and returns an order ID
     *
     * @return an order ID
     */
    @Timed
    @PostMapping(path = "/cart/checkout")
    public String checkout(@RequestBody Cart cart) {
        meterRegistry.counter("checkouts").increment();
        meterRegistry.counter("carts").increment(-1);
        Cart storedCart = cartService.getCart(cart.getId());
        for (Item item : storedCart.getItems()) {
            meterRegistry.counter("cartsvalue").increment(-1*(item.getUnitPrice()*item.getQty()));
        }
        return cartService.checkout(cart);
    }

    /**
     * Updates a shopping cart, replacing it's contents if it already exists. If no cart exists (id is null)
     * a new cart is created.
     *
     * @return the updated cart
     */
    @PostMapping(path = "/cart")
    public Cart updateCart(@RequestBody Cart cart) {
        meterRegistry.counter("carts").increment();
        Cart updatedCart = cartService.update(cart);
        for (Item item : updatedCart.getItems()) {
            meterRegistry.counter("cartsvalue").increment(item.getUnitPrice()*item.getQty());
        }
        return updatedCart;
    }

    /**
     * return all cart IDs
     *
     * @return
     */
    @GetMapping(path = "/carts")
    public List<String> getAllCarts() {
        return cartService.getAllsCarts();
    }


}