package TheClouthHouse.E_CommerseMS.CustomeException;

public class InvalidProductIDException extends Exception{
    public InvalidProductIDException(String sms){
        super(sms);
    }
}
