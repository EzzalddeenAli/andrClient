package ee.ttu.a103944.shopandr.network.response;


public class AbstractResponse {

    private Object response;

    private RequestStatus requestStatus;

    public AbstractResponse() {
        requestStatus = RequestStatus.ERROR;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public AbstractResponse setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public <T> T getResponse() {
        if (response == null) {
            return null;
        }
        return (T) response;
    }

    public AbstractResponse setResponse(Object response) {
        this.response = response;
        return this;
    }

}
