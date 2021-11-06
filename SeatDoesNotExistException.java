public class SeatDoesNotExistException extends Exception
{
    public SeatDoesNotExistException() {}

    public SeatDoesNotExistException(String message)
    {
        super(message);
    }
}