package TheClouthHouse.E_CommerseMS.Services;


import TheClouthHouse.E_CommerseMS.Entities.*;
import TheClouthHouse.E_CommerseMS.Repository.CartItemRepository;
import TheClouthHouse.E_CommerseMS.Repository.CartRepository;
import TheClouthHouse.E_CommerseMS.Repository.ProductRepository;
import TheClouthHouse.E_CommerseMS.Repository.UserRepository;
import TheClouthHouse.E_CommerseMS.RequestDtos.AddProductToCartRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.RemoveProductFromCartRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.UpdateQuantityRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.ViewCartDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public String AddProductToCart(AddProductToCartRequest addProductToCartRequest) throws  Exception{

        Optional<User> optionalUser = userRepository.findById(addProductToCartRequest.getUserId());
        Optional<Product> optionalProduct  = productRepository.findById(addProductToCartRequest.getProductId());

        if(optionalUser.isEmpty()){
            throw new Exception("Please Enter valid userId!!");
        }

        if(optionalProduct.isEmpty()){
            throw new Exception("Please Enter valid productId!!");
        }

        User user = optionalUser.get();
        Product product = optionalProduct.get();



        CartItem cartItem = new CartItem();
        cartItem.setCart(user.getCart());
        cartItem.setProduct(product);
        cartItem.setQuantity(addProductToCartRequest.getQuantity());
        cartItem.setPrice(addProductToCartRequest.getQuantity()* product.getPrice());

        Cart cart  = user.getCart();
        cart.getCartItemList().add(cartItem);


        cart.setNoOfProducts(cart.getNoOfProducts()+ addProductToCartRequest.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+ cartItem.getPrice());



        cartRepository.save(cart);


        return "New product has been added successfully to cart!!";
    }


    public String removeProductFromCart(RemoveProductFromCartRequest removeProductFromCartRequest) throws Exception{

        Optional<Product> optionalProduct  = productRepository.findById(removeProductFromCartRequest.getProductId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(removeProductFromCartRequest.getCartItemId());

        if(optionalCartItem.isEmpty()){
            throw new Exception("Please Enter valid CartId!!");
        }

        if(optionalProduct.isEmpty()){
            throw new Exception("PLease Enter valid productId!!");
        }

        Product product  = optionalProduct.get();
        CartItem cartItem  = optionalCartItem.get();

        cartItem.setQuantity(cartItem.getQuantity()-1);
        cartItem.setPrice(cartItem.getPrice()- product.getPrice());

        Cart cart  = cartItem.getCart();
        cart.setNoOfProducts(cart.getNoOfProducts()-1);
        cart.setTotalPrice(cart.getTotalPrice()- product.getPrice());


        cartItemRepository.save(cartItem);

        return "product has been removed from Cart successfully!!";

    }

    public List<ProductDetail> viewCartDetails(ViewCartDetailsRequest viewCartDetailsRequest) throws Exception{

        Optional<Cart> optionalCart  = cartRepository.findById(viewCartDetailsRequest.getCartId());

        if(optionalCart.isEmpty()){
            throw new Exception("Please Enter Valid CartID!!");
        }

        Cart cart  = optionalCart.get();

        List<CartItem> cartItemList  = optionalCart.get().getCartItemList();

        List<ProductDetail> productDetailList  = new ArrayList<>();

        for(CartItem cartItem : cartItemList){

            ProductDetail  productDetail  = ProductDetail.builder()
                    .productName(cartItem.getProduct().getProductName())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getPrice())
                    .build();

            productDetailList.add(productDetail);
        }

        return productDetailList;
    }

    public String UpdateQuantity(UpdateQuantityRequest updateQuantityRequest) throws Exception{

        Optional<Product> optionalProduct  = productRepository.findById(updateQuantityRequest.getProductId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(updateQuantityRequest.getCartItemId());

        if(optionalProduct.isEmpty()){
            throw new Exception("Enter Valid ProductID!!");
        }

        if(optionalCartItem.isEmpty()){
            throw new Exception("Enter Valid cartItemID!!");
        }

        CartItem cartItem  = optionalCartItem.get();
        Product product  = optionalProduct.get();
        Cart cart  = cartItem.getCart();

        if(updateQuantityRequest.getNewQuantity()==0)
        {
            Integer a  = cartItem.getQuantity();
            Double b  = cartItem.getPrice();

            cart.setNoOfProducts(cart.getNoOfProducts()-a);
            cart.setTotalPrice(cart.getTotalPrice()-b);

            cartRepository.save(cart);
            cartItemRepository.delete(cartItem);
            return "Product has been removed successfully!!";
        }

        int oldQty = cartItem.getQuantity();
        int newQty = updateQuantityRequest.getNewQuantity();

        // Set new quantity
        cartItem.setQuantity(newQty);

        // Update cart_item price
        cartItem.setPrice(product.getPrice() * newQty);

        // Calculate difference
        int diff = newQty - oldQty;

        if (diff > 0) {
            // Increasing quantity
            cart.setNoOfProducts(cart.getNoOfProducts() + diff);
            cart.setTotalPrice(cart.getTotalPrice() + diff * product.getPrice());
        } else {
            // Decreasing quantity
            diff = Math.abs(diff);
            cart.setNoOfProducts(cart.getNoOfProducts() - diff);
            cart.setTotalPrice(cart.getTotalPrice() - diff * product.getPrice());
        }


         cartItemRepository.save(cartItem);
         cartRepository.save(cart);

        return "Product quantity has been updated successfully!!";

    }

}
