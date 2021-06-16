import edu.iis.mto.testreactor.money.Money;
import edu.iis.mto.testreactor.offer.Discount;
import edu.iis.mto.testreactor.offer.DiscountPolicy;
import edu.iis.mto.testreactor.reservation.ClientData;
import edu.iis.mto.testreactor.reservation.Id;
import edu.iis.mto.testreactor.reservation.Reservation;
import edu.iis.mto.testreactor.reservation.Reservation.ReservationStatus;
import java.util.Date;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.lenient;
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
        //Mockito.when(discountPolicy.applyDiscount(any(), any(), any())).thenReturn(discount);
    }
    
    @Test
    void reservation_status_test()
    {
        MatcherAssert.assertThat(reservation.isClosed(), equalTo(false));
    }
    
}
