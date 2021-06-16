import edu.iis.mto.testreactor.money.Money;
import edu.iis.mto.testreactor.offer.Discount;
import edu.iis.mto.testreactor.offer.DiscountPolicy;
import edu.iis.mto.testreactor.offer.Offer;
import edu.iis.mto.testreactor.offer.OfferItem;
import edu.iis.mto.testreactor.reservation.ClientData;
import edu.iis.mto.testreactor.reservation.Id;
import edu.iis.mto.testreactor.reservation.Product;
import edu.iis.mto.testreactor.reservation.ProductType;
import edu.iis.mto.testreactor.reservation.Reservation;
import edu.iis.mto.testreactor.reservation.Reservation.ReservationStatus;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationTest{
    
    @Mock
    DiscountPolicy discountPolicy;
    
    Id id;
    ReservationStatus status;
    ClientData client;
    Date date;
    Money money;
    Discount discount;
    Reservation reservation;
    Product product1;
    Product product2;
                    
    @BeforeEach
    void setup()
    {
        id = new Id("id");
        status = ReservationStatus.OPENED;
        client = new ClientData(id, "client");
        date = new Date();
        money = new Money(1000);
        discount = new Discount("discount", money);
        reservation = new Reservation(id, status, client,date);
        product1 = new Product(id,money,"product1", ProductType.FOOD);
        product2 = new Product(id,money,"product2", ProductType.FOOD);
    }
    
    @Test
    void reservation_status_test()
    {
        MatcherAssert.assertThat(reservation.isClosed(), equalTo(false));
    }
    
    @Test
    void reservation_no_of_call_test_1()
    {
        reservation.add(product1, 1);
        reservation.add(product2, 1);
        Mockito.when(discountPolicy.applyDiscount(product1, 1, money)).thenReturn(discount);
        Mockito.when(discountPolicy.applyDiscount(product2, 1, money)).thenReturn(discount);
        reservation.calculateOffer(discountPolicy);
        
        verify(discountPolicy, times(1)).applyDiscount(product1, 1, money);
        verify(discountPolicy, times(1)).applyDiscount(product2, 1, money);
    }
    
    @Test
    void reservation_add_test()
    {
        reservation.add(product1, 1);
        reservation.add(product1, 3);
        
        Mockito.when(discountPolicy.applyDiscount(product1, 4, money)).thenReturn(discount);
        
        reservation.calculateOffer(discountPolicy);
        
        verify(discountPolicy, times(1)).applyDiscount(product1, 4, money);
    }
    
    @Test
    void reservation_calculate_test()
    {
        reservation.add(product1, 3);
        reservation.add(product2, 1);
        Mockito.when(discountPolicy.applyDiscount(product1, 3, money)).thenReturn(discount);
        Mockito.when(discountPolicy.applyDiscount(product2, 1, money)).thenReturn(discount);
        Offer offer = reservation.calculateOffer(discountPolicy);
        List<OfferItem> items = offer.getAvailabeItems();
        
        OfferItem item = items.get(0);
        
        MatcherAssert.assertThat(item.getQuantity(), equalTo(3));
        
        item = items.get(1);
        MatcherAssert.assertThat(item.getQuantity(), equalTo(1));
    }
}
